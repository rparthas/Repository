var dataArray = [5, 11, 18];

var svg = d3.select('body').append('svg').attr('width', '100%').attr('height', '100%');
svg.selectAll('rect').data(dataArray)
  .enter().append('rect')
  .attr("fill", "pink")
  .attr('height', function(d, i) {
    return 100 + d;
  })
  .attr('width', '15')
  .attr('x', function(d, i) {
    return (30 * i) + 100;
  })
  .attr('y', function(d, i) {
    return 300 - d;
  });

var newX = 300;
svg.selectAll('circle').data(dataArray)
  .enter().append('circle')
  .attr('cx', function(d, i) {
    return newX + (2 * i * d);
  })
  .attr('cy', function(d, i) {
    return newX + 15;
  })
  .attr('r', function(d, i) {
    return d;
  });

newX = 500;
svg.selectAll('ellipse').data(dataArray)
  .enter().append('ellipse')
  .attr('cx', function(d, i) {
    return newX + (8 * i * d);
  })
  .attr('cy', function(d, i) {
    return 315;
  })
  .attr('rx', function(d, i) {
    return 30 + d;
  })
  .attr('ry', function(d, i) {
    return d + 40;
  })
  .attr('stroke', 'red')
  .attr('fill', 'yellow')
  .attr('stroke-width', '2');

newX = 900;
svg.selectAll("line").data(dataArray).
enter().append("line")
  .attr('x1', function(d, i) {
    return newX;
  })
  .attr('y1', function(d, i) {
    return 315 + (3 * d);
  })
  .attr('x2', function(d, i) {
    return newX + (5 * d);
  })
  .attr('y2', function(d, i) {
    return 315 + (3 * d);
  })
  .attr('stroke', 'red')
  .attr('fill', 'yellow')
  .attr('stroke-width', '2');

newX = 400;
var textArray = ['start', 'middle', 'end'];
svg.append("text").selectAll("tspan")
  .data(textArray)
  .enter().append("tspan")
  .attr("x", newX)
  .attr("y", function(d, i) {
    return 150 + (i * 30);
  })
  .attr("fill", "none")
  .attr("stroke", "blue")
  .attr("stroke-width", "2")
  .attr("dominant-baseline", "middle")
  .attr("text-anchor", "start")
  .attr("font-size", "30")
  .text(function(d) {
    return d;
  });
