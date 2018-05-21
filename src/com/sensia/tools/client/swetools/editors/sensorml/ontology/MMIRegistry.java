/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.ontology;

import java.util.*;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.client.Window;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.ILoadOntologyCallback;
import com.sensia.tools.client.swetools.editors.sensorml.ontology.property.IOntologySearch;


public class MMIRegistry implements IOntologySearch
{
    static final String PREDICATE_DEF = "http://www.w3.org/2004/02/skos/core#definition";
    static final String PREDICATE_UNIT = "http://mmisw.org/ont/cf/parameter/canonical_units";
    String endpoint;
    
    
    public MMIRegistry(String endpoint)
    {
        this.endpoint = endpoint;
    }


    public void search(final String searchTerm, final ILoadOntologyCallback callback,List<ONTOLOGY_FILTER> filters) {

        if(filters == null || filters.size() == 0) {
            callback.onLoad(Arrays.asList(new String[]{"URI","Description","UNIT"}),new Object[0][0]);
        } else {
            String filter = "";
            filter = "filter (";
            for (int i = 0; i < filters.size(); i++) {
                if (i != 0) {
                    filter += "%0A+++%7C%7C+";
                }

                if (filters.get(i) == ONTOLOGY_FILTER.URI) {
                    filter += "regex(str(%3Fsubject),+%22" + searchTerm + "%5B%5E%2F%23%5D*$%22,+%22i%22)";
                } else if (filters.get(i) == ONTOLOGY_FILTER.DESCRIPTION) {
                    filter += "regex(str(%3Fobject),+" +
                            "%22" + searchTerm + "%22,+%22i%22)";
                }
            }
            filter += ")%0A%7D%0Aorder+by+%3Fsubject";
            String searchUrl = endpoint + "?query=select+distinct+%3Fsubject+%3Fpredicate+%3Fobject%0Awhere+%7B%0A+%3Fsubject+%3Fpredicate+%3Fobject.%0A+" + filter;

            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, searchUrl);
            builder.setHeader("Accept", "application/json");

            try {
                builder.sendRequest(null, new RequestCallback() {
                    @Override
                    public void onResponseReceived(Request request, Response response) {
                        if (response.getStatusCode() != 200)
                            showError("Error " + response.getStatusCode() + ": " + response.getStatusText());
                        else
                            parseJson(response.getText(), callback);
                    }

                    @Override
                    public void onError(Request request, Throwable e) {
                        showError(e.getMessage());
                    }
                });
            } catch (RequestException e) {
                showError(e.getMessage());
            }
        }
    }

    public void search(final String searchTerm, final ILoadOntologyCallback callback)
    {
        search(searchTerm,callback,null);
    }
    
    private void parseJson(String data, ILoadOntologyCallback callback)
    {
        JSONObject json = (JSONObject)JSONParser.parseStrict(data);        
        JSONArray triples = (JSONArray)json.get("values");


        // create one row per distinct subject with definition and unit
        Map<String, Object[]> results = new HashMap<>();
        for (int i=0; i<triples.size();i++) {
            JSONArray item = (JSONArray)triples.get(i);
            String subject = getStringValue(item.get(0));
            String predicate = getStringValue(item.get(1));
            String object = getStringValue(item.get(2));
            
            // skip weird subjects
            if (subject.startsWith(":"))
                continue;
            
            Object[] rowData = results.get(subject);
            if (rowData == null)
            {
                rowData = new Object[3];
                rowData[0] = subject;
                rowData[1] = "";
                rowData[2] = "";
                results.put(subject, rowData);
            }
            
            // parse object value depending on predicate
            if (PREDICATE_DEF.equals(predicate))
                rowData[1] = object;
            if (PREDICATE_UNIT.equals(predicate))
                rowData[2] = object;
        }
        
        List<String> headers = Arrays.asList("URI", "Description", "Unit");      
        callback.onLoad(headers, results.values().toArray(new Object[0][0]));
    }
    
    private String getStringValue(JSONValue json) {
        String val = ((JSONString)json).stringValue();
        val = val.replaceAll("\\\\\"", "\"");
        return val.substring(1, val.length()-1);
    }
    
    private void showError(String msg) {
        Window.alert("Error searching ontology\n" + msg);
    }
}
