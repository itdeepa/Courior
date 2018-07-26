<%@ include file = "/common/Include.jsp"  %>  
<%@ page import="easycbs.courior.actionform.BkngConsForm "%>
<% String sMode="Opr";

	
%>


<html>
 <head>
   <script>
   	   
    function rcvPacket(id)
	      {
			  if(id=="rcvd"){
				document.getElementById("tabId").style.display="block";  
				document.getElementById("notRcvdPkt").style.display="none";
				document.getElementById("rcvdPkt").style.display="block";
				document.getElementById("notRcvdPktDiv").style.display="none";
				document.getElementById("rcvdPktDiv").style.display="block";	
				
				document.getElementById("docTypeValue").value=document.getElementById("rcvd").value;
				//document.getElementById("reason").value="";
				document.getElementById("saveButton").style.display="block";
			  }
			  else if(id=="notrcvd"){
				  document.getElementById("tabId").style.display="block";  
				document.getElementById("rcvdPkt").style.display="none"; 
				document.getElementById("notRcvdPkt").style.display="block"; 
				document.getElementById("notRcvdPktDiv").style.display="block";
				document.getElementById("rcvdPktDiv").style.display="none";	
				
				document.getElementById("docTypeValue").value=document.getElementById("notrcvd").value;
				document.getElementById("saveButton").style.display="block";
			  }
			
		  }
		  function checkDetails(ConsNo)
		  {   
			  
			var url=appcontext +"/Courior/BkngConsAction.do?action=recConsignement&ConsNo="+ConsNo;	
			var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
	
					if (xhttp.readyState == 4 && xhttp.status == 200) {
					var resArray=xhttp.responseText;
					document.getElementById("destination").value=resArray;
					
				 }
				};
				xhttp.open("GET", url, true);
					xhttp.send();
				

		  }

		
		function gotomenu()
			{
				document.forms[0].action = appcontext +"/menu/FormOpen.do?from=cancel";
				document.forms[0].method="post";
				document.forms[0].submit();
			}
		function saveData()
		{
				document.forms[0].action=appcontext +"/Courior/BkngConsAction.do?action=recConsignement&Mode=save";	
				document.forms[0].method="post";
				document.forms[0].submit();
		}
	
	</script>
 </head>
 
 <body>
	    <div align="center">
			<label id = "title" style = "font-weight:bold; font-size:15; color:#660000;">	
				 Receiving Window <br><br>
			</label>
			<HR>
		</div>
 
  
  <table width="25%" align="center"  >
         <tr>
            <td><INPUT TYPE="Radio" NAME="rcvPacket" value="rcving"  id="rcvd" onclick="rcvPacket(this.id);"><b>Recieved Packets</b>&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td><INPUT TYPE="Radio" NAME="rcvPacket" value="notrcving" id="notrcvd" onclick="rcvPacket(this.id);"><b>Not Received Packets</b></td>
		 </tr>
  </table>

    <html:form action="/Courior/BkngConsAction" enctype="multipart/form-data" >
	<html:hidden  property="docTypeValue" styleId="docTypeValue" />
	<div align="center" id="notRcvdPktDiv" style="display:none">
				<HR>
				<label id = "title" style = "font-weight:bold; font-size:15; color:#660000;">	
					 Not Receiving Packets <br><br>
				</label>
			</div>
	<div align="center" id="rcvdPktDiv" style="display:none">
			<HR>
			<label id = "title" style = "font-weight:bold; font-size:15; color:#660000;">	
				 Receiving Packets <br><br>
			</label>
	</div>


	<table width="30%" border="0" cellspacing="0" cellpadding="0" align="center" id="tabId" style="display:none">
 		<tr >
			<td >Consignment No <font size="2"	color='<%=errorColor%>'></font></td>
			<td><B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;</B><html:text property="consNo" styleId="consNo" size="20"  onchange="checkDetails(this.value);" maxlength="100" tabindex='1'/>
			</td>
		</tr>

		<tr>
			<td  ><label>Address<font size="2" color='<%=errorColor%>'></font></label></td>
			<td><B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;</B><html:text property="destination" styleId="destination" readonly="true" size="20"  onchange="" maxlength="100" tabindex='1'/></td>
		</tr>
		
		
		<tr id="rcvdPkt" style="display:none">
			<td ><label>Signature<font size="2" color='<%=errorColor%>'></font></label></td>
			<td><B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</B>
			<input type="file" name="uploadImg" id="uploadImg">
			</td>
		</tr>
		
		
		<tr id="notRcvdPkt" style="display:none">
			<td><label> Reason <font size="2" color='<%=errorColor%>'></font></label></td>
			<td><B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</B>
			 <textarea name="reason" rows="4" cols="15" styleId="reason"></textarea>
			</td>
		</tr>
	
		
	</table>
	
	


	 <br>
	 <br>
	 <br>
	 <br>
	 <br>
	 <br>
	 <br>

	   

   <table width="20%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
	
		<td><input type="button" name="saveButton" id="saveButton"  value="<bean:message bundle='<%=lang%>' key='496'/>"  onclick ="saveData();" tabIndex='4' style="display: none;"></td>
		<td><input type="button" name="cancel" id="cancel" value="<bean:message bundle='<%=lang%>' key='87'/>" style="width:100px" onclick="gotomenu()" tabindex='5'></td>
		<td><input type="button" name="cancel" id="cancel" value="<bean:message bundle='<%=lang%>' key='33'/>" style="width:100px"  tabindex='6'></td>
		</tr>
	</table>
	<table align="center">
		<DIV id = "Div" style = "display:block;">
		<script>
				var ind = 0;
				<html:messages id = "Errors" property = "ccNo" bundle='<%=errorLang%>'>
					errorArrayValue[ind] = "accNo";
					errorArrayText[ind] = '<bean:write name = "Errors" />';
					ind = ind + 1;
				</html:messages>
				showErrorTab();
		</script>
		</DIV>
		</table>
  </html:form>

 </body>
 </html>