//			<script type="text/javascript">
// <![CDATA[
var centreLat = 0.0;
var centreLon = 0.0;
var initialZoom = 2;
var imageWraps = false; // SET THIS TO false TO PREVENT THE
// IMAGE WRAPPING AROUND

var map; // the GMap3 itself
var gmicMapType;
// marker
var locationForSearch;
var locationForPathFrom;
var locationForPathTo;
var markerForSearch;
var markerForPathFrom;
var markerForPathTo;
var infoWindowForSearch;
var infoWindowForPathFrom;
var isInAddMarkerForPathMode;

function GMICMapType() {
	this.Cache = Array();
	this.opacity = 1.0;
}
GMICMapType.prototype.tileSize = new google.maps.Size(256, 256);
GMICMapType.prototype.maxZoom = 19;
GMICMapType.prototype.getTile = function(coord, zoom, ownerDocument) {
	var c = Math.pow(2, zoom);
	var c = Math.pow(2, zoom);
	var tilex = coord.x, tiley = coord.y;
	if (imageWraps) {
		if (tilex < 0)
			tilex = c + tilex % c;
		if (tilex >= c)
			tilex = tilex % c;
		if (tiley < 0)
			tiley = c + tiley % c;
		if (tiley >= c)
			tiley = tiley % c;
	} else {
		if ((tilex < 0) || (tilex >= c) || (tiley < 0) || (tiley >= c)) {
			var blank = ownerDocument.createElement('DIV');
			blank.style.width = this.tileSize.width + 'px';
			blank.style.height = this.tileSize.height + 'px';
			return blank;
		}
	}
	var img = ownerDocument.createElement('IMG');
	var d = tilex;
	var e = tiley;
	var f = "t";
	for ( var g = 0; g < zoom; g++) {
		c /= 2;
		if (e < c) {
			if (d < c) {
				f += "q"
			} else {
				f += "r";
				d -= c
			}
		} else {
			if (d < c) {
				f += "t";
				e -= c
			} else {
				f += "s";
				d -= c;
				e -= c
			}
		}
	}
	img.id = "t_" + f;
	img.style.width = this.tileSize.width + 'px';
	img.style.height = this.tileSize.height + 'px';
	img.src = "map-tiles/" + f + ".jpg";
	this.Cache.push(img);
	return img;
}

// /////////////////////////////////////////////
// places
var places = [ [ 'nanHaiLou', -68.801997, -26.047516, 2 ],
		[ 'library', -35.250115, 10.49676, 1 ] ];

// this is test for java call javascript via WebView, call
// successfully
function printLine(arg) {

	var n = arg.split("#");
	var corr_x = [ "", "" ];
	var corr_y = [ "", "" ];

	corr_x[0] = n[0];
	corr_y[0] = n[1];
	corr_x[1] = n[2];
	corr_y[1] = n[3];
	var str = new String("");
	str = corr_x[0] + " " + corr_y[0] + " " + corr_x[1] + " " + corr_y[1];

	document.getElementById("shit").innerHTML = str;

	var flightPlanCoordinates = [
			new google.maps.LatLng(parseFloat(corr_x[0]), parseFloat(corr_y[0])),
			new google.maps.LatLng(parseFloat(corr_x[1]), parseFloat(corr_y[1])) ];
	var flightPath = new google.maps.Polyline({
		path : flightPlanCoordinates,
		strokeColor : "#FF0000",
		strokeOpacity : 1.0,
		strokeWeight : 5
	});

	flightPath.setMap(map);

}
// format latitude#longitude#infoString#tohere#more,call from
// BuildingService(Building building,String info)
// latitude,longitude comes from building , infoString comes
// from info

function addMarkerForSearch(arg) {
	
	isInAddMarkerForPathMode = "0";
	
	markerForPathFrom.setVisible(false);
	markerForPathTo.setVisible(false);
	infoWindowForPathFrom.close();
	infoWindowForPathTo.close();
	
	var n = arg.split("#");
	var latitude = n[0];
	var longitude = n[1];
	var infoString = n[2];
	var tohere = n[3];
	var more = n[4];
	/*
	 * var c = map.getCenter(), x = c.lng(), y = c.lat(); var str = y + ' ' + x;
	 */
	var str = latitude + " " + longitude + " " + infoString + " " + tohere
			+ " " + more;

	var jnu = "jnu";

	document.getElementById("shit").innerHTML = str;

	locationForSearch = new google.maps.LatLng(parseFloat(latitude),
			parseFloat(longitude));

	// reset marker,infoWindow
	markerForSearch.setPosition(locationForSearch);
	markerForSearch.setVisible(true);

	infoWindowForSearch.setContent(infoString);
	// infoWindowForSearch.setPosition(locationForSearch);

	google.maps.event.addListener(markerForSearch, 'click', function() {
		infoWindowForSearch.open(map, markerForSearch);
	});

	map.setCenter(locationForSearch);

	google.maps.event.addListener(map, 'zoom_changed', function() {

		if (map.getZoom() < initialZoom)
			map.setZoom(initialZoom);
		map.setCenter(locationForSearch);
	});
}
// format latitude#longitude#infoString#tohere#more,call from
// BuildingService(Building building,String info)
// latitude,longitude comes from building , infoString comes
// from info
function addMarkerForPath(arg) {
	isInAddMarkerForPathMode = "1";
	markerForSearch.setVisible(false);
	infoWindowForSearch.close();
	
	var n = arg.split("#");
	var latitudeFrom = n[0];
	var longitudeFrom = n[1];
	var infoStringFrom = n[2];
	var tohereFrom = n[3];
	var moreFrom = n[4];

	var latitudeTo = n[5];
	var longitudeTo = n[6];
	var infoStringTo = n[7];
	var tohereTo = n[8];
	var moreTo = n[9];

	var str = latitudeFrom + " " + longitudeFrom + " " + infoStringFrom + " "
			+ tohereFrom + " " + moreFrom + "#" + latitudeTo + " "
			+ longitudeTo + " " + infoStringTo + " " + tohereTo + " " + moreTo;

	var jnu = "jnu";

	document.getElementById("shit").innerHTML = str;

	locationForPathFrom = new google.maps.LatLng(parseFloat(latitudeFrom),
			parseFloat(longitudeFrom));

	markerForPathFrom.setPosition(locationForPathFrom);
	markerForPathFrom.setVisible(true);
	
	infoWindowForPathFrom.setContent(infoStringFrom);
	
	//infoWindowForPathFrom.open(map,markerForPathFrom);

	google.maps.event.addListener(markerForPathFrom, 'click', function() {
		infoWindowForPathFrom.open(map, markerForPathFrom);
	});

	map.setCenter(locationForPathFrom);

	google.maps.event.addListener(map, 'zoom_changed', function() {

		map.setCenter(locationForPathFrom);
		if (map.getZoom() < initialZoom)
			map.setZoom(initialZoom);
		map.setCenter(locationForSearch);
	});
	
	locationForPathTo = new google.maps.LatLng(parseFloat(latitudeTo),
			parseFloat(longitudeTo));

	markerForPathTo.setPosition(locationForPathTo);
	markerForPathTo.setVisible(true);

	infoWindowForPathTo.setContent(infoStringTo);
	
//	infoWindowForPathTo.open(map,markerForPathTo);
	google.maps.event.addListener(markerForPathTo, 'click', function() {
		infoWindowForPathTo.open(map, markerForPathTo);
	});
}

function displayPath(from, to) {

	var flightPlanCoordinates = [
			new google.maps.LatLng(-67.200796, -25.269978),
			new google.maps.LatLng(-67.200796, -10.107991),
			new google.maps.LatLng(-44.198742, -10.49676),
			new google.maps.LatLng(-43.919364, 9.330454) ];
	var flightPath = new google.maps.Polyline({
		path : flightPlanCoordinates,
		strokeColor : "#FF0000",
		strokeOpacity : 1.0,
		strokeWeight : 10
	});

	flightPath.setMap(map);

}
// ///////////////////////////////////////////////

function load() {
	
	resizeMapDiv();
	var latlng = new google.maps.LatLng(centreLat, centreLon);
	var myOptions = {
		zoom : initialZoom,
		minZoom : 0,
		maxZoom : 4,
		center : latlng,
		panControl : true,
		zoomControl : true,
		zoomControlOptions : {
			style : google.maps.ZoomControlStyle.LARGE,
			position : google.maps.ControlPosition.RIGHT_TOP
		},

		mapTypeControl : true,
		scaleControl : false,
		streetViewControl : false,
		overviewMapControl : false,
		mapTypeControlOptions : {
			mapTypeIds : [ "ImageCutter" ]
		},
		mapTypeId : "ImageCutter"
	}
	map = new google.maps.Map(document.getElementById("map"), myOptions);
	gmicMapType = new GMICMapType();
	map.mapTypes.set("ImageCutter", gmicMapType);


	// //////////////////////////////////////////////////////////////////////

	// Bounds for North America
	var strictBounds2 = new google.maps.LatLngBounds(
	// new google.maps.LatLng(28.70, -127.50),
	// new google.maps.LatLng(48.85, -55.90)
	new google.maps.LatLng(-39.677533391215306, -27.102203499999984),
			new google.maps.LatLng(39.58455, 24.72175));

	var strictBounds3 = new google.maps.LatLngBounds(
	// new google.maps.LatLng(28.70, -127.50),
	// new google.maps.LatLng(48.85, -55.90)
	new google.maps.LatLng(-73.57, -77.69), new google.maps.LatLng(73.514,
			78.36654));

	var strictBounds4 = new google.maps.LatLngBounds(
	// new google.maps.LatLng(28.70, -127.50),
	// new google.maps.LatLng(48.85, -55.90)
	new google.maps.LatLng(-81.005, -104.44), new google.maps.LatLng(80.97,
			104.3821));

	// Listen for the dragend event
	google.maps.event
			.addListener(
					map,
					'dragend',
					function() {
						if (map.getZoom() == 2) {
							if (strictBounds2.contains(map.getCenter()))
								return;

							// We're out of bounds - Move the
							// map back within the bounds

							var c = map.getCenter(), x = c.lng(), y = c.lat(), maxX = strictBounds2
									.getNorthEast().lng(), maxY = strictBounds2
									.getNorthEast().lat(), minX = strictBounds2
									.getSouthWest().lng(), minY = strictBounds2
									.getSouthWest().lat();

							if (x < minX)
								x = minX;
							if (x > maxX)
								x = maxX;
							if (y < minY)
								y = minY;
							if (y > maxY)
								y = maxY;

							map.setCenter(new google.maps.LatLng(y, x));
						} else if (map.getZoom() == 3) {

							if (strictBounds3.contains(map.getCenter()))
								return;

							// We're out of bounds - Move the
							// map back within the bounds

							var c = map.getCenter(), x = c.lng(), y = c.lat(), maxX = strictBounds3
									.getNorthEast().lng(), maxY = strictBounds3
									.getNorthEast().lat(), minX = strictBounds3
									.getSouthWest().lng(), minY = strictBounds3
									.getSouthWest().lat();

							if (x < minX)
								x = minX;
							if (x > maxX)
								x = maxX;
							if (y < minY)
								y = minY;
							if (y > maxY)
								y = maxY;

							map.setCenter(new google.maps.LatLng(y, x));
						} else if (map.getZoom() == 4) {
							if (strictBounds4.contains(map.getCenter()))
								return;

							// We're out of bounds - Move the
							// map back within the bounds

							var c = map.getCenter(), x = c.lng(), y = c.lat(), maxX = strictBounds4
									.getNorthEast().lng(), maxY = strictBounds4
									.getNorthEast().lat(), minX = strictBounds4
									.getSouthWest().lng(), minY = strictBounds4
									.getSouthWest().lat();

							if (x < minX)
								x = minX;
							if (x > maxX)
								x = maxX;
							if (y < minY)
								y = minY;
							if (y > maxY)
								y = maxY;

							map.setCenter(new google.maps.LatLng(y, x));
						}
					});
	
	// Limit the zoom level
	google.maps.event.addListener(map, 'zoom_changed', function() {
		if (map.getZoom() < initialZoom)
			map.setZoom(initialZoom);
	});

	// set/create new marker,infoWindow

	markerForSearch = new google.maps.Marker({
		position : latlng,
		map : map
	});
	markerForSearch.setVisible(false);
	
	markerForPathFrom = new google.maps.Marker({
		position : latlng,
		map : map
	});
	markerForPathFrom.setVisible(false);
	
	markerForPathTo = new google.maps.Marker({
		position : latlng,
		map : map
	});
	markerForPathTo.setVisible(false);

	infoWindowForSearch = new google.maps.InfoWindow();
	infoWindowForPathFrom = new google.maps.InfoWindow();
	infoWindowForPathTo = new google.maps.InfoWindow();
	
	//

	isInAddMarkerForPathMode = "0";

	// set listener to infowindow
	google.maps.event.addListener(map, "click", function(event) {

		var lat = event.latLng.lat();
		var lng = event.latLng.lng();
		// populate yor box/field with lat, lng
		// alert("Lat=" + lat + "; Lng=" + lng);
		/*
		 * var myVar = setTimeout(function() { alert("Hello") }, 3000);
		 */
		document.getElementById("shit").innerHTML = "Lat=" + lat + "; Lng="
				+ lng;

		// call android/java from js
		var arg = lat.toString() + "#" + lng.toString()+"#"+ isInAddMarkerForPathMode;

		document.getElementById("shit").innerHTML = arg;
		window.android.callAndroid(arg);
		infoWindowForSearch.close();
		// infoWindowForPathFrom.close();

	});

	// /////////////////////////////////////////////////////////////////////
}

GMICMapType.prototype.realeaseTile = function(tile) {
	var idx = this.Cache.indexOf(tile);
	if (idx != -1)
		this.Cache.splice(idx, 1);
	tile = null;
}
GMICMapType.prototype.name = "Map of Jnu";
GMICMapType.prototype.alt = "Image Cutter Tiles";
GMICMapType.prototype.setOpacity = function(newOpacity) {
	this.opacity = newOpacity;
	for ( var i = 0; i < this.Cache.length; i++) {
		this.Cache[i].style.opacity = newOpacity; // mozilla
		this.Cache[i].style.filter = "alpha(opacity=" + newOpacity * 100 + ")"; // ie
	}
}

function getWindowHeight() {
	if (window.self && self.innerHeight) {
		return self.innerHeight;
	}
	if (document.documentElement && document.documentElement.clientHeight) {
		return document.documentElement.clientHeight;
	}
	return 0;
}

function resizeMapDiv() {
	// Resize the height of the div containing the map.

	// Do not call any map methods here as the resize is called
	// before the map is created.
	var d = document.getElementById("map");

	var offsetTop = 0;
	for ( var elem = d; elem != null; elem = elem.offsetParent) {
		offsetTop += elem.offsetTop;

	}
	var height = getWindowHeight() - offsetTop - 16;

	if (height >= 0) {
		d.style.height = height + "px";
	}
}

// ]]>
// </script>
