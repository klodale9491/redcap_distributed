/*
* carica i progetti di un utente.
* */
function get_projects(){
    var token_value = $.cookie("token");
    var microservice_url = "http://localhost:9081/metadatamanager/api/project/";
    $.get(microservice_url,{},function(json_projects){
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
    });
}

/*
* carica un progetto in base al suo id.
* */
function get_project_by_id(project_id){
    var token_value = $.cookie("token");
    var microservice_url = "http://localhost:9081/metadatamanager/api/project/"+project_id;
    $.get(microservice_url,{},function(project){
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
    });
}


function create_project(name,description){
    var token_value = $.cookie("token");
    var microservice_url = "http://localhost:9081/metadatamanager/api/project/";
    $.ajax({
        type: 'POST',
        url: microservice_url,
        data: JSON.stringify({name:name,description:description}),
        success: function(data) {
            alert("Progetto inserito");
        },
        complete:function(){
            location.reload();
        },
        contentType: "application/json",
        dataType: 'json'
    });
}

function update_project(){

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
    var microservice_url = "http://localhost:9081/metadatamanager/api/project/"+form_id;
    $.ajax({
        url: microservice_url,
        type: 'DELETE',
        success: function(result) {
            location.reload();
        },
        error : function(xhr, error){
            alert("Errore durante l'eliminazione del form.");
        }
    });
}