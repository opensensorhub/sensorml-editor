/***************************** BEGIN LICENSE BLOCK ***************************

The contents of this file are subject to the Mozilla Public License, v. 2.0.
If a copy of the MPL was not distributed with this file, You can obtain one
at http://mozilla.org/MPL/2.0/.

Software distributed under the License is distributed on an "AS IS" basis,
WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
for the specific language governing rights and limitations under the License.
 
Copyright (C) 2012-2017 Sensia Software LLC. All Rights Reserved.
 
******************************* END LICENSE BLOCK ***************************/

package com.sensia.tools.client.swetools.editors.sensorml.ontology.property;


import java.util.List;

public interface IOntologySearch
{

    void search(String searchTerm, ILoadOntologyCallback callback);

    void search(String searchTerm, ILoadOntologyCallback callback, List<ONTOLOGY_FILTER> filters);

    enum ONTOLOGY_FILTER {
        DESCRIPTION,
        URI
    }
}
