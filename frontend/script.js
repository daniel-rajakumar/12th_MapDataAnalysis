// import d3 from "https://d3js.org/d3.v7.min.js"
// let dan = import jqfrom "https://code.jquery.com/jquery-3.6.0.min.js";
// import jQuery from "https://code.jquery.com/jquery-3.6.0.min.js";


window.ns = {
    toggle: function (element) {
        console.log("element -> " + element)
        jQuery(element).fadeToggle();
        element.style.width = "50%"
        element.style.height = "39px"
        // element.setVisible(false)
    },

    printArray : function (arr) {
        console.log(arr.length)
    },

    printMap : async function (map) {
        // const { d3 } = await import("https://d3js.org/d3.v4.min.js")
        // var str = JSON.stringify(map, null, 2)
        // console.log(str)
        console.log(JSON.parse(map));

    }
}