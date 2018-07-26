<%@ include file = "/common/Include.jsp"  %>  
<%@ page import="easycbs.courior.actionform.BkngConsForm "%>
<% String sMode="Opr";
%>
<html>
	<head>
		 <script>
			function showConsigDetail(consNo){
				if(trim(document.getElementById("searchConsNo").value)=="")
				 {
				 alert('Please Enter Consignment No.');
				 document.getElementById("searchConsNo").focus;
				 return false;
				 }
				 else {
					   
					  document.forms[0].action=appcontext +"/Courior/BkngConsAction.do?action=fetchBkngConsDetail&ConsNo="+consNo;	
					  document.forms[0].method="post";
					  document.forms[0].submit();

				      /*if(trim(document.getElementById("doc").value)="True")	
					      document.getElementById("docTypeValue").checked="checked";
				      else(trim(document.getElementById("nonDoc").value)=="True")
						 document.getElementById("docTypeValue").checked="checked";*/

				 }	 
			}
		
			function gotomenu()
			{
				document.forms[0].action = appcontext +"/menu/FormOpen.do?from=cancel";
				document.forms[0].method="post";
				document.forms[0].submit();
			}
			function fillData()
			{
				
				//alert(document.getElementById("docTypeValue").value);
				/*if(document.getElementById("docTypeValue").value=="Y")
					document.getElementById("doc").checked=true;	
				else
				 document.getElementById("nonDoc").checked=true;*/
			}

			function saveConsDetail()
			{
				 if(document.getElementById("fbrn").innerHTML!=""&& document.getElementById("fwdbrncode").value==""  ){
					 alert("Please Enter Branch code");
					 return false;
				}
				var forward=document.getElementById("fwd").checked;
				var arrival=document.getElementById("arr").checked;

				if(trim(forward)=="true")	
					 document.getElementById("fwdArrivValue").value="fwd";
				 else if(trim(arrival)=="true")
						 document.getElementById("fwdArrivValue").value="arr";

				  document.forms[0].action=appcontext +"/Courior/BkngConsAction.do?action=saveBkngConsFwdDtl";	
				  document.forms[0].method="post";
				  document.forms[0].submit();

					  document.getElementById("saveButton").disabled = true;
					  document.getElementById("cancel").disabled = true;
            }
			function showBranch(X)
			{
				var innerData= "<label >Forwarding of Branch <font size=\"2\" color=\'<%=errorColor%>\'>*</font></label> &nbsp;<b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:&nbsp;</b><input type=\"text\"id=\"fwdbrncode\"size=\"10\" maxlength=\"10\" onkeypress=\"alphaNumWithoutSpace()\"onkeydown=\"callMasterHelp('fwdbrncode')\"/><img src=\"<%=request.getContextPath()%>/images/HELP1.BMP\" class=\"Image\" onClick=\"openHelpPanel('fwdbrncode', 'destinationBrKid', 'destinationBrLabel','CBSBranchMaster')\"><input type=\"text\" property=\"destinationBrLabel\" styleId=\"destinationBrLabel\" readonly=\"true\"size=\"25\"style =\"background-color:activeborder;border:0px\" />";
				document.getElementById("fbrn").innerHTML=innerData;
								document.getElementById("DivForward").style.display="block";
			}
			function hideBranch()
			{
				document.getElementById("fbrn").innerHTML=" ";

				document.getElementById("DivForward").style.display="none";
			}
		</script>
	</head>
		
		
		<body onload="fillData()">
		<div align="center">
			<label id = "title" style = "font-weight:bold; font-size:15; color:#660000;">	
				Consignment Packet Process<br><br>
			</label>
			<HR>
		</div>

		<html:form action="/Courior/BkngConsAction" >
		<html:hidden  property="fwdArrivValue" styleId="fwdArrivValue" />
		

<table width="100%" align="center"  >
			<tr><td align="center"><b>Consignment No</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			:<html:text property="searchConsNo" styleId="searchConsNo"  onblur="showConsigDetail(this.value);" size="20" maxlength="100"/></td></tr>
				<tr><td>&nbsp;&nbsp;</td></tr>
				<tr><td align="center">
			<INPUT TYPE="radio" NAME="fwdArriv" checked="checked" id="fwd" onclick="showBranch(this.id);"><b>Forwarding of Consignment</b>&nbsp;&nbsp;&nbsp;
			&nbsp;&nbsp;&nbsp;<INPUT TYPE="radio" NAME="fwdArriv" id="arr" onclick="hideBranch(this.id);"><b>Arriving of Consignment</b></td></tr>
		</table>
<HR>

       <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" style="width:450px; margin:0 auto;">
		
		 <tr>
			<td align="left">Branch <font size="2"	color='<%=errorColor%>'></font></td>
			<td><B>:&nbsp;</B><html:text property="brnCode" styleId="brnCode" readonly="true" size="20"  onchange="" maxlength="100" tabindex='1'/>
			</td>
		</tr>

		 <tr>
			<td align="left" ><label>Consignment Date<font size="2" color='<%=errorColor%>'></font></label></td>
			<td><B>:&nbsp;</B><html:text property="consDate" styleId="consDate" readonly="true" size="20"  onchange="" maxlength="100" tabindex='1'/></td>
		</tr>
		
		<tr>
			<td align="left" ><label>Consignment No <font size="2" color='<%=errorColor%>'></font></label></td>
			<td><B>:&nbsp;</B><html:text property="consNo" styleId="consNo"   readonly="true" size="20" onchange="" maxlength="100" tabindex='1'/></td>
		</tr>

		<tr>
			<td align="left" ><label>Destination Branch<font size="2" color='<%=errorColor%>'></font></label> </td>
		    <td><B>:&nbsp;</B><html:text property="showDestbrn" styleId="showDestbrn" readonly="true" size="20"  onchange="" maxlength="100" tabindex='1'/></td>
		</tr>

		 <tr>
			<td align="left" ><label>Destination <font size="2" color='<%=errorColor%>'></font></label> </td>
		    <td><B>&nbsp;&nbsp;</B><html:textarea   property="destination" rows="2" cols="12" styleId="destination"  readonly="true" tabindex='1' /></td>
		</tr>

		 <tr>
			<td align="left"><label>Weight <font size="2" color='<%=errorColor%>'></font></label></td>
			<td><B>:&nbsp;</B><html:text property="weight" styleId="weight" readonly="true" size="20"  onchange="" maxlength="100" tabindex='1'/></td>
		</tr>

		 <tr>
			<td align="left" ><label>Dimensions <font size="2" color='<%=errorColor%>'></font></label></td>
			<td><B>:&nbsp;</B><html:text property="dimension" styleId="dimension" readonly="true" size="20"  onchange="" maxlength="100" tabindex='1'/></td>
		</tr>


		 <tr>
			<td align="left" ><label>Document Type <font size="2" color='<%=errorColor%>'></font></label></td> 
			<td><B>:&nbsp;</B><html:text property="docTypeValue" styleId="docTypeValue" readonly="true" size="20"  onchange="" maxlength="100" tabindex='1'/></td>
		 </tr> 


		</table>

		<div id="DivForward"> 
		<HR>
		<table width="160%" border="0" cellspacing="0" cellpadding="0" align="center" style="width:450px; margin:0 auto;">
		<tr>
			<td align="left" id="fbrn" style="display: block;"><label >Forwarding Branch<font size="2" color='<%=errorColor%>'>*</font></label>
			<B>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;:</B>
			<html:text  property="fwdbrncode" styleId="fwdbrncode" tabindex="90" size="10"  maxlength="10" onkeypress="alphaNumWithoutSpace()"  
			onblur="getRespectiveValues(fwdbrncode,'fwdbrncode','destinationBrLabel','destinationBr',this.value)"
			onkeydown="callMasterHelp('fwdbrncode')"/>
			<img src="<%=request.getContextPath()%>/images/HELP1.BMP"   class="Image" onClick="openHelpPanel('fwdbrncode', 'destinationBrKid', 'destinationBrLabel','CBSBranchMaster')"> 
			 <html:text property="destinationBrLabel" styleId="destinationBrLabel" readonly="true"  size="25" style ="background-color:activeborder;border:0px" tabindex="-1"/>
			</td>
		</tr>
	    </table> 
		</div>


		<table width="100%" align="center" >
		<tr>
		<td  align="center" colspan="2">
		<HR>
		<input type="button" name="saveButton" id="saveButton"  value="<bean:message bundle='<%=lang%>' key='7119'/>"  onclick ="saveConsDetail()" tabIndex='4'>
		<input type="button" name="cancel" id="cancel" value="<bean:message bundle='<%=lang%>' key='87'/>"  onclick="gotomenu()" tabindex='5'>
		</td>
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

 