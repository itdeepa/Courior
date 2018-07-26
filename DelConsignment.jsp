<%@ include file = "/common/Include.jsp"  %>  
<%@ page import="easycbs.courior.actionform.BkngConsForm "%>
<% String sMode="Opr",DelType="";
String data[][] = null;
int iLen = 0;
if(request.getParameter("DelType")!=null)
	DelType=request.getParameter("DelType");

if(session.getAttribute("Data")!=null){
		data = (String[][])session.getAttribute("Data");
		iLen = data.length;
		session.removeAttribute("Data");
	}
%>


<html>
 <head>
   <script>


		   
    function delPacket(id)
	      {
			  var delType="";
			  if(id=="incm"){
				document.getElementById("outpkt").style.display="none";
				document.getElementById("inpkt").style.display="block";
				delType=document.getElementById("incm").value;
				document.getElementById("isAll").style.display="block";
				document.getElementById("cancel").style.display="block";
			  }
			  else if(id=="out"){
				document.getElementById("inpkt").style.display="none";
				document.getElementById("outpkt").style.display="block"; 
				delType=document.getElementById("out").value;
			  }
			 document.forms[0].action=appcontext +"/Courior/BkngConsAction.do?action=deliveryCons&DelType="+delType;
			 document.forms[0].method="post";
			 document.forms[0].submit();


			
		  }

	function gotomenu()
			{
				document.forms[0].action = appcontext +"/menu/FormOpen.do?from=cancel";
				document.forms[0].method="post";
				document.forms[0].submit();
			}
	function fillData()
			{
			var delType='<%=DelType%>';
				if(delType=='incoming'){
					document.getElementById("outpkt").style.display="none";
					document.getElementById("inpkt").style.display="block";
					document.getElementById("incm").checked;
					document.getElementById("isAll").style.display="block";
					document.getElementById("cancel").style.display="block";
				
				}
				
				else if(delType=='outgoing'){
				document.getElementById("inpkt").style.display="none";
				document.getElementById("outpkt").style.display="block";
				document.getElementById("out").checked;
				}
			}
				function checkAll()
					{
						var i = <%= iLen%>;
						var text = (document.getElementById("isAll").value);
						if(i!=0)
						if(text == "Select all")
						{
							for(var l = 0 ; l < i ; l++)
							document.getElementById("chk" + l).checked = true ;
							document.getElementById("isAll").value = '<bean:message bundle="<%=lang%>" key="2822"/>' ;
						}
						else
						{
							for(var l = 0 ; l < i ; l++)
							document.getElementById("chk" + l).checked = false ;
							document.getElementById("isAll").value = '<bean:message bundle="<%=lang%>" key="607"/>' ;
						}
						else
							window.alert("'<bean:message bundle="<%= errorLang  %>" key="308"/>'");
					}
					function saveDetails()
						{
								var i = <%=iLen%>;
								var ConsNo='';
								document.getElementById("consNo").value ="";
								for(l=0;l<i;l++)
								{
									if(document.getElementById("chk" + l).checked)
									{
										ConsNo += document.getElementById("chk" + l).value+",";
									}
								}   
								ConsNo = ConsNo.substring(0, ConsNo.length-1);
								if(ConsNo=="")
								{
								  window.alert("'<bean:message bundle="<%= errorLang  %>" key="405"/>'");
								}
								else
								{
								  document.getElementById("consNo").value = ConsNo;	
								  document.forms[0].action =appcontext +"/Courior/BkngConsAction.do?action=saveDeliveryDtl";
								  document.forms[0].submit();	
								}


						}
   </script>
 </head>
 
 <body onload="fillData();">
 <div align="center">
			<label id = "title" style = "font-weight:bold; font-size:15; color:#660000;">	
				 Consignment Delivery Packets <br><br>
			</label>
			<HR>
		</div>
  <table width="30%" align="center"  >
         <tr>
            <td><INPUT TYPE="Radio" NAME="delPacket" value="incoming"  id="incm" onclick="delPacket(this.id);"><b>Incoming Packets</b></td>
            <td><INPUT TYPE="Radio" NAME="delPacket" value="outgoing" id="out" onclick="delPacket(this.id);"><b>Outgoing Packets</b></td>
       
 </table>
    <html:form action="/Courior/BkngConsAction"  >
	<html:hidden property = "consNo" styleId = "consNo"/>
    <table width="70%" border="1" cellspacing="0" cellpadding="0" align="center" id="inpkt" style="display:none">
	<div align="center">
			<HR>
			<label id = "title" style = "font-weight:bold; font-size:15; color:#660000;">	
				 Incoming Packets <br><br>
			</label>
		</div>
       <tr>
			<th>&nbsp;</th>
            <th>Consignment Number <font size="2" color='<%=errorColor%>'></font></th>
			<th >Packet Source<font size="2" color='<%=errorColor%>'></font></th>
			<th>Packet Destination  <font size="2" color='<%=errorColor%>'></font></th>
			<th >Packet Type Doc/Non-Doc<font size="2" color='<%=errorColor%>'></font> </th>
			<th>Weight <font size="2" color='<%=errorColor%>'></font> </th>
	        <th>Dimensions <font size="2" color='<%=errorColor%>'></font> </th>
		</tr>
		<tr>
		<%if(data!=null && data[0].length>2){ %>
			<% for(int i=0; i<data.length; i++){ %>
		<tr>
		 <td ><input type="checkbox"   id ="chk<%=i%>" onClick="setSelRowValue('<%=i%>')" value="<%=data[i][0]%>" >  </td>
			<td align="center"><a href="javascript:openWindow('<%=data[i][0]%>',msg)"><%=data[i][0]%></a></td>
			<td align="center"><%=data[i][1]%></td>
			<td align="center"><%=data[i][2]%></td>
			<td align="center"><%=data[i][3]%></td>
			<td align="center"><%=data[i][4]%></td>
			<td align="center"><%=data[i][5]%></td>
		</tr> 
			<%}%>
			<%}else {%>
				<tr><td colspan="2">No data available</td></tr>
			<%}%>

		</tr>
	
		
   </table>
    
    <table width="30%" border="1" cellspacing="0" cellpadding="0" align="center" id="outpkt" style="display:none">
	<div align="center">
			<HR>
			<label id = "title" style = "font-weight:bold; font-size:15; color:#660000;">	
				 Outgoing Packets <br><br>
			</label>
		</div>
	   <tr>
	      <th>Consignment Number <font size="2" color='<%=errorColor%>'></font></th>
		  <th>Reason <font size="2" color='<%=errorColor%>'></font></th>
	   </tr>
	   <tr>
	   <%if(data!=null){ %>
			<% for(int i=0; i<data.length; i++){ %>
		<tr>
	  <td align="center"><%=data[i][0]%></td>
			<td align="center"><%=data[i][1]%></td>
		</tr> 
			<%}%>
			<%}else {%>
				<tr><td colspan="2">No data available</td></tr>
			<%}%>

		</tr>
	  
	 </table>
	 <br>
	 <br>
	 <br>
	 <br>
	 <br>
	 <br>
	 <br>
	<table width="70%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td  align="center" colspan="2">
			
			<tr>
			<tr>
			<td></td>
		</tr>
	    </table>

   <table width="20%" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
		
		 <td><INPUT id="isAll"  type="button" value="<bean:message bundle='<%=lang%>' key='607'/>" name="isAll" onclick="checkAll()" style="display: none;"></td>
		 <td><input type="button" name="cancel" id="cancel" value="<bean:message bundle='<%=lang%>' key='496'/>" style="width:100px" onclick="saveDetails()" style="display: none;" ></td>
		<td><input type="button" name="cancel" id="cancel" value="<bean:message bundle='<%=lang%>' key='87'/>" style="width:100px" onclick="gotomenu()"				tabindex='5'></td>
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