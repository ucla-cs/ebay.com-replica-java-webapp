<html>
<head>
    <title>ResultSite</title>
</head>
<body>
<%@ page import="java.util.*" %>

	<form name="input" action="http://localhost:8080/eBay/search" method="get">

	Search keyword: <input type="text" name="q" />
	<INPUT TYPE="hidden" NAME="numResultsToSkip" VALUE="0">
	<INPUT TYPE="hidden" NAME="numResultsToReturn" VALUE="15">
	
	<input type="submit" value="Submit" />
	
	</form> 

    Search results for keyword --> 
    <%= request.getAttribute("q") %> 
    
    <% if( (Integer)request.getAttribute("srLength")!=0 && !request.getAttribute("q").equals("")){%>
    
    ( <%= (Integer)request.getAttribute("numResultsToSkip") %> -  <%= (Integer)request.getAttribute("numResultsToSkip") +15 %> )
    
    
    <% 
    int test =40;
    int length = (Integer)request.getAttribute("srLength");

    %>

    <TABLE BORDER=2>
    <TR><TD>ItemID</TD><TD>ItemName<TD>
	<%
    	for(int i=0; i< length; i++ ) {
       	 %>
        	<TR>
        	<TD><%= request.getAttribute("itemId"+i) %></TD>
        	<TD><%= request.getAttribute("itemName"+i) %></TD>
        	<TD>
	        	<form name="input" action="http://localhost:8080/eBay/item" method="get">
	
				<input type="hidden" name="id" VALUE="<%= request.getAttribute("itemId"+i) %>"/></br>
				<input type="submit" value="View" />
    			</form>
        	</TD>
        	</TR>
        <%
    	}
        %>
     </TABLE>
     
     <TABLE > <TR><TD>
     <% if( 15 == (Integer)request.getAttribute("srLength") && length>0 ) { %>
     <form name="input" action="http://localhost:8080/eBay/search" method="get">

		<input type="hidden" name="q" VALUE="<%= request.getAttribute("q") %>"/></br>
		<INPUT TYPE="hidden" NAME="numResultsToSkip" VALUE="<%=    (Integer)request.getAttribute("numResultsToSkip") +15 %>"></br>
		<INPUT TYPE="hidden" NAME="numResultsToReturn" VALUE="15"></br>

		<input type="submit" value="Next" />
    </form>
    <% } %>
    </TD><TD>
     <% if( ((Integer)request.getAttribute("numResultsToSkip") )>0 ) { %>
     <form name="input" action="http://localhost:8080/eBay/search" method="get">

		<input type="hidden" name="q" VALUE="<%= request.getAttribute("q") %>"/></br>
		<INPUT TYPE="hidden" NAME="numResultsToSkip" VALUE="<%=    (Integer)request.getAttribute("numResultsToSkip") -15 %>"></br>
		<INPUT TYPE="hidden" NAME="numResultsToReturn" VALUE="15"></br>

		<input type="submit" value="Prev" />
    </form>
    <% } %>
    </TD></TR></TABLE>
    
     <% } else{%>
     
     <br /><b>No Item Found.</b>
     
     <% } %>
</body>
</html>
