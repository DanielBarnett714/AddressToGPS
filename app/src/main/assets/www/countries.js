var states = ["Alabama", "Alaska", "Arizona", "Arkansas", "California",
    "Colorado", "Connecticut", "Delaware", "District of Columbia",
    "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
    "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts",
    "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana",
    "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
    "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma",
    "Oregon", "Pennsylvania", "Rhode Island", "South Carolina",
    "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia",
    "Washington", "West Virginia", "Wisconsin", "Wyoming"
];
var sel = document.getElementById('state');
for (var i = 0; i < states.length; i++) {
    var opt = document.createElement('option');
    opt.innerHTML = states[i];
    opt.value = states[i];
    sel.appendChild(opt);
}
document.getElementById('state').onchange = function() {
    document.getElementById('address').value = document.getElementById(
            'lookup').value + " " + document.getElementById('city').value +
        " " + document.getElementById('state').value;
};
document.getElementById('lookup').onkeyup = function() {
    document.getElementById('address').value = document.getElementById(
            'lookup').value + " " + document.getElementById('city').value +
        " " + document.getElementById('state').value;
};
document.getElementById('city').onkeyup = function() {
    document.getElementById('address').value = document.getElementById(
            'lookup').value + " " + document.getElementById('city').value +
        " " + document.getElementById('state').value;
};