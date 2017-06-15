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

//295,289,282
//300,300,300
