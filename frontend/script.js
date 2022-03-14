/*
    credit: https://www.d3-graph-gallery.com/graph/pie_changeData.html
    https://observablehq.com/@hydrosquall/simple-linear-regression-scatterplot-with-d3
 */

let isSalary;
window.ns = {

    drawLinearReg : function (map_one, map_two) {
        drawLinearRegGraph(map_one, map_two)
    },

    drawPie : function (one, two) {
        drawPieCharts();

        ns.updatePie(one, two, true)
    },

    updatePie : function (one, two, isDefault){
        isSalary = isDefault;
        let map_1 = new Map(Object.entries(JSON.parse(two)));
        let map_2 = new Map(Object.entries(JSON.parse(one)));


        const data1 = {
            left: map_1.get("salary").starting_salary,
            right: map_2.get("salary").starting_salary,
        }

        const data2 = {
            left: map_1.get("unemployment").unemployment_rate,
            right: map_2.get("unemployment").unemployment_rate,
        }

        update(isDefault ? data1 : data2)
    }




}


const pie = {
    width: 500,
    height:500,
    margin: 40
}

function drawPieCharts() {
    // set the dimensions and margins of the graph
    // append the svg object to the div called 'my_dataviz'
    d3.select("#chart_pie")
        .append("svg")
        .attr("width", pie.width)
        .attr("height", pie.height)
        .append("g")
        .attr("transform", "translate(" + pie.width / 2 + "," + pie.height / 2 + ")");

}






const local = d3.local();
function update(data) {
    const radius = Math.min(pie.width, pie.height) / 2 - pie.margin

    // set the color scale
    const color = d3.scaleOrdinal()
        .domain(["salary", "unemployment"])
        .range(d3.schemeDark2);

    const arc = d3.arc()
        .innerRadius(0)
        .outerRadius(radius)

    // Compute the position of each group on the pie:
    const d3pie = d3.pie()
        .value(function (d) {
            return d.value;
        })
        .sort(function (a, b) {
            // console.log(a);
            return d3.ascending(a.key, b.key);
        });


    // This make sure that group order remains the same in the pie chart
    const data_ready = d3pie(d3.entries(data));

    // console.log(data)

    // map to data
    let u = d3.selectAll("#chart_pie").select("svg").selectAll("g")
                    // .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")")
                    .selectAll("path")
                    .data(data_ready);


    // Build the pie chart: Basically, each part of the pie is a path that we build using the arc function.
    u
        .enter()
        .append('path')
        .each(function(d) {
            local.set(this, d)
        })
        .merge(u)
        .transition()
        .duration(1000)
        .attrTween('d', function(d) {
            const i = d3.interpolate(local.get(this), d);
            local.set(this, i(0));
            return function(t) {
                return arc(i(t));
            };
        })
        .attr('fill', function(d){
            return(color(d.data.key))
        })
        .attr("stroke", "white")
        .style("stroke-width", "2px")
        .style("opacity", 1)

    // add text
     u = d3.selectAll('.pieText').node()
        ? d3.selectAll('.pieText')
        : u.enter().append('text').attr("class", "pieText")

    u
        .data(data_ready)
        .transition()
        .duration(1000)
        .attr("transform", function(d) {
            d.innerRadius = radius
            return "translate(" +  arc.centroid(d) + ")";
        })
        .style("text-anchor", "middle")
        .attr("dy", "0.66em")
        .text(function(d) {
            console.log(isSalary)
            if (isSalary) {
                const money = d.value.toLocaleString('en-US', {
                    style: 'currency',
                    currency: 'USD',
                });

                console.log(money)
                return money.slice(0, -3);
            } else{
                return d.value + "%";
            }
        });

    // u
    //     .data(data_ready)
    //     .enter()
    //     .append('text')
    //     .text(function(d){ return d.data.key})
    //     .attr("transform", function(d) { return "translate(" + arcGenerator.centroid(d) + ")";  })
    //     .style("text-anchor", "middle")
    //     .style("font-size", 17)

    // remove the group that is not present anymore
    u.exit()
        .remove()

}


function random(min, max) {
    return Math.random() * (max - min) + min;
}

function drawLinearRegGraph(map_salary, map_unemployment){
    let map_1 = new Map(Object.entries(JSON.parse(map_salary)));
    let map_2 = new Map(Object.entries(JSON.parse(map_unemployment)));

    const margin = ({ top: 50, right: 20, bottom: 20, left: 50 })
    const height = 500
    const width = 1000

    const data = require('lodash').zipWith(Array.from(map_1.values()), Array.from(map_2.values()), Array.from(map_2.keys()),
        (salary, unemployment, name) => ({
            x: salary.starting_salary,
            y: unemployment.unemployment_rate,
            z: name
        })
    )

    const xScale = d3.scaleLinear()
        .domain([d3.min(data, d => d.x) - 1000, d3.max(data, d => d.x)])
        .range([margin.left, width - margin.right])

    const yScale = d3.scaleLinear()
        .domain([d3.min(data, d => d.y) - 0.5, d3.max(data, d => d.y)]) // added a bit of breathing room (20,000)
        .range([height - margin.bottom, margin.top])

    const xAxis = g => g.attr('transform', `translate(0, ${height - margin.bottom})`)
        .attr("class", "xAxis")
        .call(d3.axisBottom(xScale))

    const yAxis = g => g.attr('transform', `translate(${margin.left}, 0)`)
        .attr("class", "yAxis")
        .call(d3.axisLeft(yScale))

    const ss = require('simple-statistics')

    const linearRegression = ss.linearRegression(data.map(d => [d.x, d.y]))

    const linearRegressionLine = ss.linearRegressionLine(linearRegression)


    const regressionPoints = () => {
        const firstX = d3.min(data, d => d.x);
        const lastX =  d3.max(data, d => d.x);
        const xCoordinates = [firstX, lastX];

        return xCoordinates.map(d => ({
            x: d,                         // We pick x and y arbitrarily, just make sure they match d3.line accessors
            y: linearRegressionLine(d)
        }));
    }

    // console.log(regressionPoints())

    const line = d3.line()
        .x(d => xScale(d.x))
        .y(d => yScale(d.y))

    const tooltip = d3.select(".chart")
        // .data(data)
        .append("div")
        .style("opacity", 0)
        .attr("class", "tooltip")
        .style("background-color", "white")
        .style("border", "solid")
        .style("border-width", "1px")
        .style("border-radius", "5px")
        .style("font-size", "1.5em")



    // A function that change this tooltip when the user hover a point.
    // Its opacity is set to 1: we can now see it. Plus it set the text and position of tooltip depending on the datapoint (d)

    const mouseover = function() {
        tooltip
            .style("opacity", 1)
            .style("padding", "10px")
    }


    const mousemove = function(d, i) {
        tooltip
            .html(i.z)
            .style("position", "absolute")
            .style("top", (d.pageY - 60) + "px")
            .style("left", (d.pageX + 10) + "px")
    }

    // A function that change this tooltip when the leaves a point: just need to set opacity to 0 again
    const mouseleave = function() {
        tooltip
            .transition()
            .duration(200)
            .style("opacity", 0)
    }


    const renderChart = (target) => {

        // First, let's make the scatterplot
        target.selectAll('circle')
            .data(data)
            .enter().append('circle')
            .attr('r', 7)
            .attr('cx', d => xScale(d.x))
            .attr('cy', d => yScale(d.y))
            .on("mouseover", mouseover )
            .on("mousemove", mousemove )
            .on("mouseleave", mouseleave )

        // Next, we'll draw the regression line
        target.append('path')
            .classed('regressionLine', true)
            .datum(regressionPoints())
            .attr('d', line);

        // Lastly, we add the axes!
        target.append('g')
            .call(xAxis);
        target.append('g')
            .call(yAxis);
    }


    const svg = d3.select(".chart")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .attr("padding", "400px")
        .append("g")
        .attr("transform",
            "translate(" + margin.left + "," + -margin.top + ")")


    // X axis label:
    svg.append("text")
        .attr("text-anchor", "end")
        .attr("x", width/2 + margin.left)
        .attr("y", height + margin.top - 20)
        .attr("class", "axis")
        .text("Starting Salary ($)");

    // Y axis label:
    svg.append("text")
        .attr("text-anchor", "end")
        .attr("transform", "rotate(-90)")
        .attr("y", 0)
        .attr("x", -margin.top - height/2 + 100)
        .attr("class", "axis")
        .text("Unemployment Rate (%)");

    renderChart(svg)
}

