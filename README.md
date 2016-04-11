# SMLEditor
A web based viewer/editor to create your sensorML document. This editor allows to view any SensorML documents (V2.0) and edits the current content. The project has been designed using GWT.

Architecture design:
-------------------

The processor transforms a XML document into a RNG instance. This instance is then processed by a Renderer to create 
the corresponding widgets of the current parsed tags. Thus each widget will add its children and create a custom panel
to represent the whole node. Depending on the content of the sub-elements, the same Widget can have different representations
according to the SensorML Specifications.
