# SMLEditor
A web based viewer/editor to create your sensorML document. This SensorML viewer/editor is used by OSH but can also be used as a standalone web editor.This editor allows to view any SensorML documents (V2.0) and edit the current content. The project has been designed using GWT.

Architecture design:
-------------------

The processor transforms a XML document into a RNG instance. This instance is then processed by a Renderer to create 
the corresponding widgets of the current parsed tags. Thus each widget will add its children and create a custom panel
to represent the whole node. Depending on the content of the sub-elements, the same Widget can have different representations
according to the SensorML Specifications.
<br />
<br />
<br />
<br />
![alt tag](https://cloud.githubusercontent.com/assets/6577027/14419378/c052a3ce-ffc5-11e5-946a-0addfc097125.png)
