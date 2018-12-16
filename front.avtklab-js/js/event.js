APIEndPoint = 'http://localhost:8080/api/v1';

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
        async: true,
        beforeSend: auth,
        success: function(data){
            data.forEach(element => addEventItem(element));             
        }
    });
}

function addEventItem(item){
    let divItem =
    `<div class="col-md-12 au-task__item au-task__item--warning">
        <div class="au-task__item-inner">
            <h5 class="time">{description}</h5>
            <div class="text-right"><p class="">De {startDate} até {endDate}</p></div>
        </div>
    </div>`
    .replace('{description}', item.description)
    .replace('{startDate}', item.startDate)
    .replace('{endDate}', item.endDate);

    $('.js-event-list').append(divItem);
}

