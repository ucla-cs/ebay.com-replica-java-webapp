<html>
<%@ page import="java.util.*" %>
<%@ page import="edu.ucla.cs.cs144.*" %>

<% Item it = (Item)request.getAttribute("item"); 
       ArrayList<CatToItem> c = (ArrayList<CatToItem>)request.getAttribute("catToItem"); 
    	ItemBuyPrice ibp = (ItemBuyPrice)request.getAttribute("itemBuyPrice"); 
    	ArrayList<Bid> b = (ArrayList<Bid>)request.getAttribute("bid"); 
    	User s = (User)request.getAttribute("seller");
%>

<head>
    <title>Item Result</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" /> 
	<style type="text/css"> 
	  html { height: 100% } 
	  body { height: 100%; margin: 0px; padding: 0px } 
	  #map_canvas { height: 100% } 
	</style> 
	<script type="text/javascript" src="http://maps.google.com/maps/api/js?sensor=false"> 
	</script> 
	<script type="text/javascript"> 
		
		var map;
		var geocoder;
	  function initialize() {
		  //var latlng = new google.maps.LatLng(34.063509,-118.44541); 
		    var myOptions = { 
		      zoom: 7, 
		      mapTypeId: google.maps.MapTypeId.ROADMAP 
		    }; 
		    map = new google.maps.Map(document.getElementById("map_canvas"), 
		        myOptions); 
		    address();
		  } 
		  
		function address(){
			 
			//geocoder = new GClientGeocoder();
			var x,y;
			var postcode = '<%= s.getLocation() %>,<%= s.getCountry()%>';
			
	        geocoder = new google.maps.Geocoder();             
	        
	        geocoder.geocode({ 'address': postcode }, function (results, status) { 
	        			 
	                    if (status == google.maps.GeocoderStatus.OK) {
	                         var marker = new google.maps.Marker({
	                            position: results[0].geometry.location,
	                            map: map,
	                            title: postcode
	                        });            
	                     
	                        google.maps.event.addListener(marker, 'click', function() {
	                            new google.maps.InfoWindow({
	                                content: '<p>This is the location of postcode <b>' + postcode + '.</b></p>'
	                            }).open(map, marker);
	                        });
	                        
	                         map.setCenter(results[0].geometry.location);
	                         //map.panTo(results[0].geometry.location);
	                    } else {
	                        map.setCenter(new google.maps.LatLng(34.063509,-118.44541) );
	                        map.setZoom(1);
	                        alert('Postcode not found');
	                    }
	                    
	                });
            }
              
	
	</script> 
</head>
<body onload="initialize()">

	<form name="input" action="http://localhost:8080/eBay/item" method="get">

	Lookup Item: <input type="text"  id="txt1" name="id" />
	
	<input type="submit" value="Submit" />
	
	</form> 

	
    
    <Table> 
    <tr>
    <td>ID</td>
    <td><%= it.getItemId() %></td>
    </tr>
    
    <tr>
    <td>Name</td>
    <td><%= it.getName() %></td>
    </tr>
    
    <tr>
    <td>Categories</td>
    <td>
    <%for(int i=0;i< c.size(); i++){%>
    	<%= c.get(i).getCategory() %> -
    <%}%>
    
    </td>
    </tr>
    
    <% if(ibp.getBuyPrice()!=0){%>
    <tr>
    <td>Buy Price</td>
    <td>
    
    	<%= ibp.getBuyPrice() %>
    
    </td>
    </tr>
    <%}%>
    

    
    <tr>
    <td>Name </td>
    <td>
    
    	<%= s.getUserId() %>
    
    </td>
    </tr>
    
    <tr>
    <td>Seller Rating </td>
    <td>
    
    	<%= s.getRating() %>
    
    </td>
    </tr>
    
    <tr>
    <td>Seller Location </td>
    <td>
    
    	<%= s.getLocation() %>
    
    </td>
    </tr>
    
    <tr>
    <td>Seller Country </td>
    <td>
    
    	<%= s.getCountry() %>
    
    </td>
    </tr>
    
    <tr>
    <td>Map</td>
    <td>
    
    <div id="map_canvas" style="width:300px; height:300px"></div> 
	
    </td>
    </tr>
    
    <tr>
    <td>Current Bid</td>
    <td><%= it.getCurrently() %></td>
    </tr>
    
    <tr>
    <td>Current </td>
    <td><%= it.getCurrently() %></td>
    </tr>
    
    <tr>
    <td>First Bid </td>
    <td><%= it.getFirstBid() %></td>
    </tr>
    
    <tr>
    <td>Number Of Bids </td>
    <td><%= it.getNumberOfBids() %></td>
    </tr>
    
    <tr>
    <td>Started at date: </td>
    <td><%= it.getStarted() %></td>
    </tr>
    
    <tr>
    <td>Ends at date: </td>
    <td><%= it.getEnds() %></td>
    </tr>
    
    <tr>
    <td>Seller </td>
    <td><%= it.getSeller() %></td>
    </tr>
    
    <tr>
    <td>Description</td>
    <td><%= it.getDescription() %></td>
    </tr>
    

	<% if(b.size()!=0){%>
    <tr>
    <td><h2>Bids</h2></td>
    </tr>
    
    <%}%>
	
    <%for(int i=0;i< b.size(); i++){%>
    	<table border="1">
    	<tr>
    	<td >Bidder </td> <td>
    	<%= b.get(i).getUid()%>
    	</td></tr>
    	<tr>
    	<td >Rating</td> <td>
    	<%= b.get(i).getU().getRating()%>
    	</td></tr>
    	<tr>
    	<td />Time <td>
    	<%= b.get(i).getTime()%>
    	</td></tr>
    	<tr>
    	<td />Amount <td>
    	<b><%= b.get(i).getAmount()%></b>
    	</td></tr>
    	<tr>
    	<td />Location <td>
    	<%= b.get(i).getU().getLocation()%>
    	</td></tr>
    	<tr>
    	<td /> Country<td>
    	<%= b.get(i).getU().getCountry()%>
    	</td>
    	</tr>
    	</table>
    	
    <%}%>
    
	</Table>
     
    
</body>
</html>
