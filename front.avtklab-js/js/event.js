APIEndPoint = 'http://localhost:8080/api/v1';
APIEndPoint = 'https://av-tklab-api.herokuapp.com/api/v1'

function authenticate(){

    let basicAuth = "Basic " + btoa($('input[name="username"]').val() + ":" + $('input[name="password"]').val());
    
    $.ajax({
        type: "GET",
        url: APIEndPoint,
        async: false,
        beforeSend: function (xhr) {xhr.setRequestHeader ("Authorization", basicAuth);},
        error: function(data){
            if(data.status == '401'){
                sessionStorage.setItem("isAuthenticated", "false");
            }else{
                sessionStorage.setItem("Authorization", basicAuth);
                sessionStorage.setItem("isAuthenticated", "true");
            }
        }
    });
    return isAuthenticated();
}

function isAuthenticated(){
    return sessionStorage.getItem("isAuthenticated") == "true";
}


function auth(xhr){
    xhr.setRequestHeader ("Authorization", sessionStorage.getItem("Authorization"));
}

function listAll(){
    $.ajax({
        type: "GET",
        url: APIEndPoint + "/event",
        beforeSend: auth,
        success: function(data){
            data.forEach(element => addEventItem(element));             
        },
        error: function(data){
            alert('Ocorreu um erro na operação, mais informações visualize o console.');
            console.error(data);
        }
    });
}

function addEventItem(item){
    let divItem =
    `<div class="col-md-12 au-task__item au-task__item--warning">
        <div class="au-task__item-inner">
            <h5 class="time">{description}</h5>
            <a href="./form.html?id={id}" class="btn btn-primary pull-right btn-sm">Editar</a>
            <div class="text-left"><p class="">De {startDate} até {endDate}</p></div>
        </div>
    </div>`
    .replace('{description}', item.description)
    .replace('{startDate}', item.startDate)
    .replace('{endDate}', item.endDate)
    .replace('{id}', item.id);

    $('.js-event-list').append(divItem);
}

function findById(){

    let id = getUrlVars()["id"];
    if(id == undefined) return

    $.ajax({
        type: "GET",
        url: APIEndPoint + "/event/" + id,
        beforeSend: auth,
        success: function(data){
            $('input[name="event.description"]').val(data.description);
            $('input[name="event.startDate"]').val(data.startDate);
            $('input[name="event.endDate"]').val(data.endDate);
        },
        error: function(data){
            alert('Ocorreu um erro na operação, mais informações visualize o console.');
            console.error(data);
        }
    });
}

function create(){

    let params = {
        'description' : $('input[name="event.description"]').val(),
        'startDate' : $('input[name="event.startDate"]').val(),
        'endDate' : $('input[name="event.endDate"]').val()
    }

    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: APIEndPoint + "/event",
        beforeSend: auth,
        data: JSON.stringify(params),
        success: function(data){
            window.location.href = "./index.html";
        },
        error: function(data){
            alert('Ocorreu um erro na operação, mais informações visualize o console.');
            console.error(data);
        }
    });
}


function update(){

    let id = getUrlVars()["id"];
    if(id == undefined) return
   
    let params = {
        'id' : id,
        'description' : $('input[name="event.description"]').val(),
        'startDate' : $('input[name="event.startDate"]').val(),
        'endDate' : $('input[name="event.endDate"]').val()
    }

    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: APIEndPoint + "/event",
        beforeSend: auth,
        data: JSON.stringify(params),
        success: function(data){
            window.location.href = "./index.html";
        },
        error: function(data){
            alert('Ocorreu um erro na operação, mais informações visualize o console.');
            console.error(data);
        }
    });
}

function remove(){

    let id = getUrlVars()["id"];
    if(id == undefined) return

    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: APIEndPoint + "/event/" + id,
        beforeSend: auth,
        success: function(data){
            window.location.href = "./index.html";
        },
        error: function(data){
            alert('Ocorreu um erro na operação, mais informações visualize o console.');
            console.error(data);
        }
    });
}