package com.sensia.tools.client.swetools.editors.sensorml.ontology;

import com.google.gwt.user.cellview.client.CellTable;

public interface TableRes extends CellTable.Resources
{
@Source({CellTable.Style.DEFAULT_CSS, "com/sensia/tools/client/swetools/editors/sensorml/ontology/ontology-table.css"})
TableStyle cellTableStyle();
 
interface TableStyle extends CellTable.Style {}
}