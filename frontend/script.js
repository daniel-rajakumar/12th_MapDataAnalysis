window.ns = {

    drawLinearReg : function (map_one, map_two) {
        drawLinearRegGraph(map_one, map_two)
    },

    drawPie : function (one, two) {
        drawPieCharts();

        ns.updatePie(one, two, true)
    },

    updatePie : function (one, two, isDefault){
        let map_1 = new Map(Object.entries(JSON.parse(one)));
        let map_2 = new Map(Object.entries(JSON.parse(two)));

        const data1 = {
            one: map_1.get("salary").starting_salary,
            two: map_2.get("salary").starting_salary,
        }

        const data2 = {
            one: map_1.get("unemployment").unemployment_rate,
            two: map_2.get("unemployment").unemployment_rate,
        }

        if (isDefault) update(data1)
        else           update(data2)
    }




}

function drawPieCharts() {
    // set the dimensions and margins of the graph
    const width = 500
    const height = 500

    // append the svg object to the div called 'my_dataviz'
    d3.select("#chart_pie")
        .append("svg")
        .attr("width", width)
        .attr("height", height)
        .append("g")
        .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")");
}

function update(data) {
    const width = 500
    const height = 500
    const margin = 40

    // The radius of the pieplot is half the width or half the height (smallest one). I subtract a bit of margin.
    let radius = Math.min(width, height) / 2 - margin

    // set the color scale
    const color = d3.scaleOrdinal()
        .domain(["salary", "unemployment"])
        .range(d3.schemeDark2);

    // Compute the position of each group on the pie:
    const pie = d3.pie()
        .value(function (d) {
            return d.value;
        })
        .sort(function (a, b) {
            // console.log(a);
            return d3.ascending(a.key, b.key);
        });


    // This make sure that group order remains the same in the pie chart
    const data_ready = pie(d3.entries(data));

    // console.log(data)

    // map to data
    const u = d3.selectAll("#chart_pie").select("svg").selectAll("g")
                    .attr("transform", "translate(" + width / 2 + "," + height / 2 + ")")
                    .selectAll("path")
                    .data(data_ready);

    // Build the pie chart: Basically, each part of the pie is a path that we build using the arc function.
    u
        .enter()
        .append('path')
        .merge(u)
        .transition()
        .duration(1000)
        .attr('d', d3.arc()
            .innerRadius(0)
            .outerRadius(radius)
        )
        .attr('fill', function(d){ return(color(d.data.key)) })
        .attr("stroke", "white")
        .style("stroke-width", "2px")
        .style("opacity", 1)

    // remove the group that is not present anymore
    u.exit()
        .remove()

}

// A function that create / update the plot for a given variable:



function drawLinearRegGraph(map_salary, map_unemployment){
    let map_1 = new Map(Object.entries(JSON.parse(map_salary)));
    let map_2 = new Map(Object.entries(JSON.parse(map_unemployment)));

    const margin = ({ top: 50, right: 20, bottom: 20, left: 50 })
    const height = 500
    const width = 1000

    const data = require('lodash').zipWith(Array.from(map_1.values()), Array.from(map_2.values()),
        (salary, unemployment) => ({
            x: salary.starting_salary,
            y: unemployment.unemployment_rate
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


    const regressionPoints = function(){
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


    const renderChart = (target) => {

        // First, let's make the scatterplot
        target.selectAll('circle')
            .data(data)
            .enter().append('circle')
            .attr('r', 5)
            .attr('cx', d => xScale(d.x))
            .attr('cy', d => yScale(d.y));

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

    // svg.append("text")
    //     .attr("text-anchor", "end")
    //     .attr("x", width/2 + margin.left)
    //     .attr("y", height + margin.top + 20)
    //     .text("sepal length");


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

