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

    printMap : function (map) {
        // const { d3 } = await import("https://d3js.org/d3.v4.min.js")
        // var str = JSON.stringify(map, null, 2)
        // console.log(str)
        console.log(JSON.parse(map));

    }
}


// let header = document.getElementsByClassName('header-right');
// let btns = elements.querySelectorAll('a');

// btns.forEach( button => {
    //set click event
    // button.addEventListener('click', function() {
    //     //remove all the active class from buttons
    //     btns.forEach( oldButton => {
    //         oldButton.classList.remove('active');
    //     });
    //
    //     //append the active class to the new clicked button
    //     this.classList.add('active');
    // })
// })