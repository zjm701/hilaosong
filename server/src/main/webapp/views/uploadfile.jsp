<%@ page session="false"%>

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script language="JavaScript">

function Validate()

{

var image =document.getElementById("image").value;

if(image!=''){

var checkimg = image.toLowerCase();

if (!checkimg.match(/(\.jpg|\.png|\.JPG|\.PNG|\.jpeg|\.JPEG)$/)){

alert("Please enter Image File Extensions .jpg,.png,.jpeg");

document.getElementById("image").focus();

return false;

}

}

return true;

}

</script>


<form:form modelAttribute="uploadItem" method="post"

enctype="multipart/form-data" onSubmit="return Validate();">

<fieldset><legend>Upload File</legend>

<table>

<tr>

<td><form:label for="fileData" path="fileData">File</form:label><br />

</td>

<td><form:input path="fileData" id="image" type="file" /></td>

</tr>

<tr>

<td><br />

</td>

<td><input type="submit" value="Upload" /></td>

</tr>

</table>

</fieldset>

</form:form>

