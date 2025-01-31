var nodes = new vis.DataSet([
    {id: 1, label: "UNL"},
    {id: 2, label: "UTPL"},
    {id: 3, label: "UPM"},
    {id: 4, label: "USC"},
    {id: 5, label: "ACT"},
    {id: 6, label: "IPN"},
    {id: 7, label: "UANL"},
    {id: 8, label: "UABC"},
    {id: 9, label: "UVM"},
    {id: 10, label: "SMA"},
  ]);

  var edges = new vis.DataSet([
    {from: 1, to: 2, label: "500.4", arrows: "to", color: "#2B7CE9"},
    {from: 1, to: 3, label: "300.3", arrows: "to", color: "#2B7CE9"},
    {from: 2, to: 4, label: "700.2", arrows: "to", color: "#2B7CE9"},
    {from: 3, to: 5, label: "400.8", arrows: "to", color: "#2B7CE9"},
    {from: 4, to: 6, label: "600.9", arrows: "to", color: "#2B7CE9"},
    {from: 5, to: 7, label: "800.1", arrows: "to", color: "#2B7CE9"},
    {from: 6, to: 8, label: "900.3", arrows: "to", color: "#2B7CE9"},
    {from: 7, to: 9, label: "1000.7", arrows: "to", color: "#2B7CE9"},
    {from: 10, to: 1, label: "1000.7", arrows: "to", color: "#2B7CE9"},
]);
var container = document.getElementById("mynetwork");
var data = {
    nodes: nodes,
    edges: edges
};

var options = {
    nodes: {
        shape: "circle",
        size: 20,
        font: {
            size: 20,
            color: "#ffffff",
            face: "Arial",
            bold: "true"
        },
        borderWidth: 4,
        color: {
            background: "#1E88E5",
            border: "#0D47A1",
            highlight: {
                background: "#FFC300",
                border: "#FF5733"
            }
        }
    },
    edges: {
        width: 3,
        font: {
            size: 14,
            color: "#333333",
            align: "middle"
        },
        color: {
            color: "#2B7CE9",
            highlight: "#FFC300"
        },
        arrows: {
            to: {enabled: true, scaleFactor: 1.3}
        },
        smooth: {
            type: "continuous"
        }
    },
    physics: {
        enabled: true,
        stabilization: false
    },
    layout: {
        improvedLayout: true
    },
    interaction: {
        hover: true,
        dragNodes: true,
        zoomView: true,
        dragView: true
    }
};

  
  var network = new vis.Network(container, data, options);