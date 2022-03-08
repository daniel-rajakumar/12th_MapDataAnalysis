// import d3 from "https://d3js.org/d3.v7.min.js"
// let dan = import jqfrom "https://code.jquery.com/jquery-3.6.0.min.js";
// import jQuery from "https://code.jquery.com/jquery-3.6.0.min.js";


import DOM from "d3/dist/d3";

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

        let linearRegression = d3.regressionLinear()
            .x(d => d.x)
            .y(d => d.y)
            .domain([-1.7, 16]);


        const margin = {left: 50, right: 50, top: 50, bottom: 50};
        const innerWidth = 100;
        const innerHeight = 100;
        // const svg = d3.select(DOM.svg(innerWidth + margin.left + margin.right, innerHeight + margin.top + margin.bottom))

        const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
        svg.setAttribute('style', 'border: 1px solid black');
        svg.setAttribute('width', '600');
        svg.setAttribute('height', '250');
        svg.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xlink", "http://www.w3.org/1999/xlink");

        document.getElementById('outlet').appendChild(svg);
        // createNode()
    }
}
function createNode() {
    const svg = d3.select(DOM.svg(innerWidth + margin.left + margin.right, innerHeight + margin.top + margin.bottom))

    const g = svg.append("g")
        .attr("transform", `translate(${margin.left}, ${margin.top})`);

    g.append("g")
        .attr("class", "axis")
        .call(xAxisLinear)
        .selectAll(".tick")
        .classed("baseline", d => d == 0);

    g.append("g")
        .attr("class", "axis")
        .attr("transform", `translate(${innerWidth})`)
        .call(yAxisLinear)
        .selectAll(".tick")
        .classed("baseline", d => d == 0);

    g.selectAll("circle")
        .data(dataLinear)
        .enter().append("circle")
        .attr("r", 3)
        .attr("cx", d => xScaleLinear(d.x))
        .attr("cy", d => yScaleLinear(d.y));

    g.append("line")
        .attr("class", "regression")
        .datum(linearRegression(dataLinear))
        .attr("x1", d => xScaleLinear(d[0][0]))
        .attr("x2", d => xScaleLinear(d[1][0]))
        .attr("y1", d => yScaleLinear(d[0][1]))
        .attr("y2", d => yScaleLinear(d[1][1]));

    return svg.node();
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