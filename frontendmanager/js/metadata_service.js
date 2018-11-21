/*
* carica i progetti di un utente.
* */
function get_projects(){
    var token_value = $.cookie("token");
    var microservice_url = "http://localhost:9081/metadatamanager/api/project/";
    $.ajax({
        type : "GET",
        url: microservice_url,
        dataType : "json",
        headers : {"Authorization" : "Bearer " + token_value},
        complete : function(json_projects){
            json_projects = json_projects.responseJSON;
            if(json_projects !== undefined){
                for(var i = 0; i < json_projects.length; i++){
                    var project_id = json_projects[i][0];
                    var project_name = json_projects[i][1];
                    var project_html = '<tr>\n' +
                        '                        <td onclick = "location.href=\'project_details.html?pid='+project_id+'\'" scope="row" style="line-height: 30px;">'+project_name+'</td>\n' +
                        '                        <td>\n' +
                        '                            <div style="float:right">\n' +
                        '                                <button type="button" class="btn btn-light"><i class="material-icons" style="color:#3498db">info</i></button>\n' +
                        '                                <button onclick = "location.href=\'project_details.html?pid='+project_id+'\'" type="button" class="btn btn-light"><i class="material-icons">edit</i></button>\n' +
                        '                                <button onclick = "delete_project_wrapper('+project_id+')" type="button" class="btn btn-light"><i class="material-icons" style="color:#e74c3c">delete</i></button>\n' +
                        '                            </div>\n' +
                        '                        </td>\n' +
                        '                </tr>';
                    $("#project_table").append(project_html);
                }
            }
        }
    });
}

/*
* carica un progetto in base al suo id.
* */
function get_project_by_id(project_id){
    var token_value = $.cookie("token");
    var microservice_url = "http://localhost:9081/metadatamanager/api/project/"+project_id;
    $.ajax({
        type : "GET",
        url: microservice_url,
        dataType : "json",
        headers : {"Authorization" : "Bearer " + token_value},
        complete : function(project){
            project = project.responseJSON;
            if(project !== undefined){
                $("#project_name_id").val(project.name);
                $("#project_description_id").val(project.description);
                // ciclo ora sui forms
                project.forms.forEach(function(form){
                    var form_html = '<tr>\n' +
                        '                        <td onclick = "location.href=\'form_build.html?fid='+form.id+'\'" scope="row" style="line-height: 30px;">'+form.name+'</td>\n' +
                        '                        <td>\n' +
                        '                            <div style="float:right">\n' +
                        '                                <button type="button" class="btn btn-light"><i class="material-icons" style="color:#3498db">info</i></button>\n' +
                        '                                <button onclick = "location.href=\'form_build.html?fid='+form.id+'\'" type="button" class="btn btn-light"><i class="material-icons">edit</i></button>\n' +
                        '                                <button onclick = "delete_form_wrapper('+form.id+')" type="button" class="btn btn-light"><i class="material-icons" style="color:#e74c3c">delete</i></button>\n' +
                        '                            </div>\n' +
                        '                        </td>\n' +
                        '                </tr>';
                    $("#form_table").append(form_html);
                });
            }
        }
    });
}

/*
* carica un progetto in base al suo id
* */
function get_form_by_id(form_id){
    var token_value = $.cookie("token");
    var microservice_url = "http://localhost:9081/metadatamanager/api/forms/"+form_id;
    $.ajax({
        type : "GET",
        url: microservice_url,
        dataType : "json",
        headers : {"Authorization" : "Bearer " + token_value},
        complete : function(form){
            form = form.responseJSON;
            if(form !== undefined){
                $("#form_name_id").val(form.name);
                // ciclo ora sui campi
                form.fields.forEach(function(field){
                    var field_html = '<li class="p1 mb1 navy component" style="position: relative; z-index: 10" draggable="true" role="option" aria-grabbed="false">\n' +
                        '             <div class="row" style="margin:0">\n' +
                        '                      <div class="col col-xs-10 col-sm-10" style="padding:0">\n' +
                        '                              <p>Tipo : '+field.variable + ', Nome : '+field.label+'</p>\n'+
                        '                      </div>\n' +
                        '                      <div class="col col-xs-2" style="padding:6px 5px"><a href="#" style="float:right;"><i onclick = "delete_field_wrapper('+field.id+')" class="material-icons" style="color:#e74c3c">delete</i></a></div>\n' +
                        '            </div>\n' +
                        '         </li>';
                    $("#form_list_build").append(field_html);
                });
            }
        }
    });
}


function create_project(name,description){
    var token_value = $.cookie("token");
    var microservice_url = "http://localhost:9081/metadatamanager/api/project/";
    $.ajax({
        type: "POST",
        url: microservice_url,
        headers : {"Authorization" : "Bearer " + token_value},
        dataType: 'json',
        contentType : 'application/json',
        data: JSON.stringify({name:name,description:description}),
        success: function(data) {
            alert("Progetto inserito");
        },
        complete:function(){
            location.reload();
        }
    });
}


/*
* aggiunge un campo al form
* */
function add_field_to_form(field_type,field_label) {
    var token_value = $.cookie("token");
    var urlParams = new URLSearchParams(location.search);
    if(urlParams.has('fid')){
        var form_id = urlParams.get('fid');
        var microservice_url = "http://localhost:9081/metadatamanager/api/forms/"+form_id+"/fields/";
        $.ajax({
            type: 'POST',
            url: microservice_url,
            headers : {"Authorization" : "Bearer " + token_value},
            data: JSON.stringify({
                form : {id:urlParams.get('fid')}, // id del form
                label : field_label,
                variable : field_type
            }),
            success: function(data) {
                alert("Campo inserito");
            },
            complete:function(){
                location.reload();
            },
            contentType: "application/json",
            dataType: 'json'
        });
    }
}


function delete_project_wrapper(project_id){
    if(confirm("Sei sicuro di cancellare il progetto e tutti i dati ad esso associati?")){
        delete_project(project_id);
    }
}

function delete_project(project_id){
    var token_value = $.cookie("token");
    var microservice_url = "http://localhost:9081/metadatamanager/api/project/"+project_id;
    $.ajax({
        url: microservice_url,
        headers : {"Authorization" : "Bearer " + token_value},
        type: 'DELETE',
        success: function(result) {
            location.reload();
        },
        error : function(xhr, error){
            alert("Errore durante l'eliminazione del progetto.");
        }
    });
}

function delete_form_wrapper(form_id){
    if(confirm("Sei sicuro di cancellare il form e tutti i dati ad esso associati?")){
        delete_form(form_id);
    }
}

function delete_form(form_id){
    var token_value = $.cookie("token");
    var microservice_url = "http://localhost:9081/metadatamanager/api/forms/"+form_id;
    $.ajax({
        url: microservice_url,
        type: 'DELETE',
        headers : {"Authorization" : "Bearer " + token_value},
        success: function(result) {
            location.reload();
        },
        error : function(xhr, error){
            alert("Errore durante l'eliminazione del form.");
        }
    });
}

function delete_field_wrapper(field_id){
    if(confirm("Sei sicuro di cancellare il campo e tutti i dati ad esso associati?")){
        delete_field(field_id);
    }
}

function delete_field(field_id){
    var urlParams = new URLSearchParams(location.search);
    if(urlParams.has('fid')){
        var token_value = $.cookie("token");
        var form_id = urlParams.has('fid');
        var microservice_url = "http://localhost:9081/metadatamanager/api/forms/"+form_id+'/fields/'+field_id;
        $.ajax({
            url: microservice_url,
            type: 'DELETE',
            headers : {"Authorization" : "Bearer " + token_value},
            success: function(result) {
                location.reload();
            },
            error : function(xhr, error){
                alert("Errore durante l'eliminazione del campo.");
            }
        });
    }
}