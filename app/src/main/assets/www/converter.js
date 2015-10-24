var geocoder;
var map;
var marker = null;
var fromPlace = 0;
var locationFromPlace;

function initialize() {
    var address = document.getElementById('address');
    autocomplete = new google.maps.places.Autocomplete(address);
    google.maps.event.addListener(autocomplete, 'place_changed', function() {
        var place = autocomplete.getPlace();
        if (!place.geometry) {
            fromPlace = 0;
            getgps();
            return;
        }
        fromPlace = 1;
        locationFromPlace = place.geometry.location;
        getgps();
    });
    geocoder = new google.maps.Geocoder();
    var latlng = new google.maps.LatLng();
    var myOptions = {}
    map = new google.maps.Map(document.getElementById("map_canvas"));
    google.maps.event.addListener(map, 'click');
}

function openInMaps() {
    var link = document.getElementById("coordlink").innerHTML;
    window.JSInterface.openMap(link);
}

function setfocus() {
    document.getElementById("lookup").onkeyup();
    document.getElementById("address").focus();
}

function getgps() {
        var address = document.getElementById("address").value;
        if (fromPlace == 1) {
            map.setCenter(locationFromPlace);
            if (marker != null) marker.setMap(null);
            marker = new google.maps.Marker({
                map: map,
                position: locationFromPlace
            });
            latt = locationFromPlace.lat();
            long = locationFromPlace.lng();
            var coordlink = latt + "," + long;
            document.getElementById("coordtext").innerHTML = coordlink;
            document.getElementById("coordlink").innerHTML = "geo:" + coordlink;
            document.getElementById("latitude").value = latt;
            document.getElementById("longitude").value = long;
        }

}
  




