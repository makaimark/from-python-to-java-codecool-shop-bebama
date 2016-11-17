/**
 * Created by cickib on 2016.11.14..
 */

$(".dropdown-toggle").click(function () {
    $("#input-cat").val("");
    $("#input-sup").val("");
})


function filter(filterby) {
    var input, filter, ul, li, a;

    if (filterby === "cat") {
        input = document.getElementById("input-cat");
        filter = input.value.toUpperCase();
        div = document.getElementById("search-cat");
        a = div.getElementsByTagName("a");

    }
    else if (filterby === "sup") {
        input = document.getElementById("input-sup");
        filter = input.value.toUpperCase();
        div = document.getElementById("search-sup");
        a = div.getElementsByTagName("a");

    }
    for (var i = 0; i < a.length; i++) {
        if (a[i].innerHTML.toUpperCase().indexOf(filter) > -1) {
            a[i].style.display = "";
        } else {
            a[i].style.display = "none";
        }
    }
}

var modal = document.getElementById("productsModal");

var button = document.getElementById("cart");

function productsWriter(products) {
    $(".product").html("");
    var table = document.getElementById("productsTable");
    for (var j = 0; j < products.length-1; j++) {
        var row = table.insertRow();
        row.setAttribute("class", "product");
        var cell = row.insertCell(0);
        cell.innerHTML = products[j].name;
        var cell = row.insertCell(1);
        var input = document.createElement("input");
        input.type = "number";
        input.name = "quantity_" + products[j].id;
        input.value = products[j].quantity;
        input.setAttribute("min", 0);
        input.setAttribute("max", 100);
        cell.appendChild(input);

        var cell = row.insertCell(2);
        cell.innerHTML = products[j].price;
        var cell = row.insertCell(3);
        var total = products[j].totalPrice;
        cell.innerHTML = total.toString() + " USD";
        //var cell = row.insertCell(4);
    }
    var row = table.insertRow();
    row.setAttribute("class", "product");
    var cell = row.insertCell(0);
    var cell = row.insertCell(1);
    var cell = row.insertCell(2);
    cell.innerHTML = "Total price:";
    var cell = row.insertCell(3);
    cell.innerHTML = products[products.length-1].totalPrice.toString() + " USD";
}

$(button).click(function () {
    clickOnCartEventHandler();
})

function clickOnCartEventHandler() {
    $.ajax({
        type: "GET",
        url: "/showcart",
        dataType: "json",
        success: function (data) {
            productsWriter(data)
        },
    });
}