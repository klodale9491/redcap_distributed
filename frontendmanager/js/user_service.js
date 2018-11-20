/*
** Effettua la login dell'utente :
** Se la login è corretta il microservizio rilascerà un token crittografato JWT.
**/
function login(usr,psw){
    var user_service_address = "http://localhost:9080/usermanager/api/users/auth";
    $.ajax({
        type: 'POST',
        url:user_service_address,
        data: {usr : usr, psw : psw},
        dataType: "text"
    }).done(function(data){
        if(data.toString() != ""){
            $.cookie("token",data.toString());
            location.href = "home.html";
        }
        else{
            alert("Accesso Negato")
        }
    }).fail(function(xhr,textStatus,error){
        alert("fail")
    });
}