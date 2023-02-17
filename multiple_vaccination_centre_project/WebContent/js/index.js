
//script eseguito al caricamento del document	
$(document).ready(function() {
	
	/**** ALERT MESSAGE ****/
	
	//alert message
	if ($("div#alertMsg").length) { 
		if($("div#alertMsg span").length){
			$("div#alertMsg").show(500);
		}else{
			$("div#alertMsg").hide();
		}
	}
	
	//close alert message
	if ($("div#alertMsg").length) { 
		$("button #closeAlertMsg").click( ()=> {
			$("div#alertMsg").fadeOut(500);
		});
	}
	
	/**** END ALERT MESSAGE ****/
	
	/*** HEADER INFO UTENTE ***/
	if($("#infoUtenteModaleDatiPersonali").length){
		$("#modifica_dati_utente_loggato").click(()=>{
			$(location).attr("href", "/googlielmo93/view/modifica_utenza.jsp");
		})
	}

	/*** END INFO UTENTE ***/
	
	
	/**** FORM LOGIN ****/
	
	//CHECKVALIDITY FORM LOGIN
	$("#login_form").submit(function(event) {
        if ($("#login_form")[0].checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        //aggiungo la classe errore/successo di bootstrap
        $("#login_form").addClass('was-validated');
    });
	
	/**** END FORM LOGIN ****/
	
	
	/**** FORM REGISTRAZIONE ****/
	
	//MOSTRA UN MESSAGGIO DOVE CHIEDE SE SETTARE L'USERNAME UGUALE ALLA EMAIL
	$("#email").focusout(function(event) {
        if ($("#email")[0].checkValidity() === true && $("#userName").val() !== $("#email").val()) {
        	$(".emailAsUserName").show(300);
        }else{
        	$(".emailAsUserName").hide(300);
        }
    });
	
	//SETTA L'USERNAME UGUALE ALLA EMAIL
	$("#emailAsUserNameEvent").click(function(event) {
		$(".emailAsUserName").hide(300);
		$("#userName").val($("#email").val());
    });
	
	$("#emailAsUserNameClose").click(function(event) {
		$(".emailAsUserName").hide(300);
    });
	
	
	
	
	//INIZIO SEZIONE PRENOTAZIONE FORM
	
	//se esiste il tag con id registration_form
	if ($("#registration_form").length) {
		
		if ($("#ruoloBean").val() != undefined && $("#ruoloBean").val() == 'user') {
        	$(".userGroupInput").show();
        	$(".userElementInput").prop('required', true);

			loadRegioniGenerico("#regioneResidenza", $("#regioneResBean").val());

			loadProvinceGenerico("#provinciaResidenza", $("#regioneResBean").val(), $("#provinciaResBean").val());
			
			loadComuniGenerico("#cittaResidenza", $("#regioneResBean").val(), $("#provinciaResBean").val(), $("#cittaResBean").val());
			$(".showResidenza").show();
			
			$("#"+$("#sessoResBean").val()).prop('checked', true);
		}else{
			//carico le regioni, le province e i comuni
			loadRegioniGenerico("#regioneResidenza");
			
			$("#regioneResidenza").change(function(){
				$("#provinciaReg").show(500);
				loadProvinceGenerico("#provinciaResidenza", $("#regioneResidenza option:selected").val());
			});
			
			$("#provinciaResidenza").change(function(){
				$("#provinciaReg").show(500);
				$("#cittaReg").show(500);
				loadComuniGenerico("#cittaResidenza", $("#regioneResidenza option:selected").val(), $("#provinciaResidenza option:selected").val());
			});
			
			$(".userGroupInput").hide();
        	$(".userElementInput").prop('required',false);
		}
		

			$("#"+$("#ruoloBean").val()).prop('checked', true);
			
			if($("#sessoResBean").val() !="N/D")
				$("#"+$("#sessoResBean").val()).prop('checked', true);
				
	}
	
	
	//CHECKVALIDITY FORM UPDATE DATI LOGIN
	$("#upload_utenza_form").submit(function(event) {
		if ( $("#password")[0].checkValidity() === false ){
			event.preventDefault();
	        event.stopPropagation();
	        $("#alertMsg").empty();
	        $("#alertMsg").show(500);
	        $("#alertMsg").append("<span><b>Attenzione &egrave; necessario l'inserimento della vecchia password o di una nuova!</b></span>");
		}
		//aggiungo la classe errore/successo di bootstrap
        $("#registration_form").addClass('was-validated');
	});
	

	//CHECKVALIDITY FORM REGISTRAZIONE
	$("#registration_form").submit(function(event) {
		$("#alertMsg").hide();
		//se loggato
		if (  $("#beanReqExist").val() == "false" &&  $("#ruoloBean").val() === $('input[type=radio][name=ruolo_toggle]:checked').val()){
			event.preventDefault();
	        event.stopPropagation();
	        $("#alertMsg").empty();
	        $("#alertMsg").show(500);
	        $("#alertMsg").append("<span>Attenzione non puoi inserire una nuova utenza con lo stesso ruolo con cui sei loggato!" +
	        		" Se desideri modificare i tuoi dati puoi richiederne la modifica cliccando in alto a destra le tre linee > dati personali >" +
	        		" modifica, e seguendo la procedura indicata nella pagina che verra&agrave; aperta.</span>");
		}
		
		let valToggle =  $('input[type=radio][name=ruolo_toggle]:checked').val();
			if( $("#userName")[0].checkValidity() === false ||
				$("#email")[0].checkValidity() === false ||
				$("#password")[0].checkValidity() === false ||
				$("#codiceFiscale")[0].checkValidity() === false ||
				$("#nome")[0].checkValidity() === false ||
				$("#cognome")[0].checkValidity() === false
			) {
	          event.preventDefault();
	          event.stopPropagation();
	        }
			else 
        	if( valToggle == "user" &&
        		$("#dataNascita")[0].checkValidity() === false ||
        		$("#cittaResidenza")[0].checkValidity() === false ||
        		$("#provinciaResidenza")[0].checkValidity() === false ||
        		$("#regioneResidenza")[0].checkValidity() === false
        	){
        		$("#dataNascita").val("");
        		$("#cittaResidenza").val("");
        		$("#provinciaResidenza").val("");
        		$("#regioneResidenza").val("");
        		
        		event.preventDefault();
  	          	event.stopPropagation();
	        }
        
        //aggiungo la classe errore/successo di bootstrap
        $("#registration_form").addClass('was-validated');
    });
	
	
	//MOSTRA O NASCONDE I CAMPI DEL FORM DI REGISTRAZIONE IN BASE AL RUOLO
	$('input:radio[name="ruolo_toggle"]').change(
	    function(){
	        if ($(this).is(':checked') && $(this).val() == 'user') {
	            	$(".userGroupInput").show();
	            	$(".userElementInput").prop('required', true);
            }else {
            	$(".userGroupInput").hide();
            	$(".userElementInput").prop('required',false);
            }
	        
	        //se loggato e se il ruolo e' uguale a quello dell'utente loggato
	        if ( $("#beanReqExist").val() == "false" && $("#ruoloBean").val() === $('input[type=radio][name=ruolo_toggle]:checked').val()){
            	$(".readOnlyClass").prop('readonly', true);
	        }else {
        		$(".readOnlyClass").prop('readonly', false);
        	}
	});

	
	
	$('input:radio[name="ruolo_toggle"]:checked').trigger("change")
	
	$('input:radio[name="sesso_toggle"]:checked').trigger("change")
	
	
	/**** END FORM REGISTRAZIONE ****/
	
	
	/**** PAGINA UTENTE ****/
	
	//apri info dati prenotazione
	if ($("#openInfo").length) {
		
		$("#openInfo").click( ()=> {
			$("#arrowInfoIcon").remove();
			
			if($("#divInfoPrenotazione").is(":visible")){
				$("#divInfoPrenotazione").hide(500);
				$("#codInfo").append("<i id=\"arrowInfoIcon\" class=\"fas fa-angle-down centraverticale\" style=\"color: #17a2b8!important; margin-left: 20%!important;\" aria-hidden=\"true\"></i>")
			}
			
			if($("#divInfoPrenotazione").is(":hidden")){
				$("#divInfoPrenotazione").show(500);
				$("#codInfo").append("<i id=\"arrowInfoIcon\" class=\"fas fa-angle-up centraverticale\" style=\"color: #17a2b8!important; margin-left: 20%!important;\" aria-hidden=\"true\"></i>")
			}
		});
	}

	
	//open form per effettuare la prenotazione
	$("#inserisciPrenotazione").click(function(){
		if($("#divPrenotazione").is(":visible")){
			$("#divPrenotazione").hide(500);
		}
		else if($("#divPrenotazione").is(":hidden")){
			
			if($("#divInfoPrenotazioneArchivio").is(":visible")){
				$("#divInfoPrenotazioneArchivio").hide(500);
			}
			
			$("#divPrenotazione").show(500);
		}
	})
	
	
	//caricamento sezione somministrazione
	if($("#sezioneSomministrazione").length){
		$("#sezioneSomministrazione").hide();
		loadInfoSomministrazione();
	}
	
	/**** END PAGINA UTENTE ****/
	
	
	
	/**** PAGINA ACCETTAZIONE ****/
	
	//caricamento sezione approvazione
	if($("#sezioneFormApprovazione").length){
		verificaApprovazione();
		
		$("#approvazione_form").submit(function(event) {
	        event.preventDefault();
	        event.stopPropagation();

	        //aggiungo la classe errore/successo di bootstrap
	        $("#approvazione_form").addClass('was-validated');
	        
	        richiestaApprovazione();
		});
	}
	
	//open form per mettere a turno
	$("#inserisciTurno").click(function(){
		$("#sezioneMettiATurnoSuccessResponse").empty();
		$("#sezioneMettiATurnoSuccessResponse").hide();
		$("#div_fail_insert_turno").empty();
		$("#div_fail_insert_turno").hide();
		$("#codiceUtenteTurno").val("");
		
		$("#mettiATurnoDiv").removeClass('was-validated');
		
		if($("#sezioneMettiATurno").is(":visible")){
			$("#sezioneMettiATurno").hide(500);
		}
		else if($("#sezioneMettiATurno").is(":hidden")){
			$("#sezioneMettiATurno").show(500);
		}
	})
	
	
	//invia metti a turno
	$("#arrowCodiceUtenteTurno").click(function(){
		mettiATurno();
	})
	
	if($("#iconTurnoSala").length){
		$("#turnoSalaAsp").click(() => {
			if (window.open('/googlielmo93/view/salaAttesa.jsp?opAsp=true', '_blank')) {
			    window.focus();
			} else {
				//se il browser blocca il redirect
			    alert("<div class=\"alert alert-warning\" role=\"alert\">Attenzione popup disabilitati sul tuo browser!</div>");
			}
		})
		
	}
	
	
	/**** END PAGINA ACCETTAZIONE ****/
	
	
	/**** PAGINA ASP ****/
	
	if($("#abilitaDipendenteIcon").length){
		$("#abilitaDipendenteIcon").click(function(){
			if($("#sezioneFormAsp").length){
				//open form per approvazione dipendenti
				$("#sezioneAspSuccessResponse").empty();
				$("#sezioneAspSuccessResponse").hide();
				$("#sezioneApprovazioneFailResponse").empty();
				$("#sezioneApprovazioneFailResponse").hide();
				$("#sezioneFormDateApertura").hide();
				$("#sezioneFormCentroVaccinaleAdd").hide();
				
				loadSelectApprova();
				
				if($("#sezioneFormAsp").is(":visible")){
					$("#sezioneFormAsp").hide(500);
				}
				else if($("#sezioneFormAsp").is(":hidden")){
					$("#sezioneFormCentroVaccinaleAdd").hide(500);
					$("#sezioneFormDateApertura").hide(500);
					$("#sezioneFormAsp").show(500);
				}
			}
		});
	}
	
	
	if($("#dateAperturaIcon").length){
		$("#dateAperturaIcon").click(function(){
			if($("#sezioneFormDateApertura").length){
				//open form per approvazione dipendenti
				$("#sezioneAspSuccessResponse").empty();
				$("#sezioneAspSuccessResponse").hide();
				$("#sezioneApprovazioneFailResponse").empty();
				$("#sezioneApprovazioneFailResponse").hide();
				$("#sezioneFormCentroVaccinaleAdd").hide();
				$("#sezioneFormAsp").hide();
				
				
				loadSelectDateApertura();
				
				if($("#dataAperturaInsert").length) {
					var d = new Date();

					var mese = d.getMonth()+1;
					var giorno = d.getDate();

					var today = d.getFullYear() + '-' +
					    (mese<10 ? '0' : '') + mese + '-' +
					    (giorno<10 ? '0' : '') + giorno;
					
					$("#dataAperturaInsert").attr("min", today)
				}
				
				if($("#sezioneFormDateApertura").is(":visible")){
					$("#sezioneFormDateApertura").hide(500);
				}
				else if($("#sezioneFormDateApertura").is(":hidden")){
					$("#sezioneFormAsp").hide(500);
					$("#sezioneFormCentroVaccinaleAdd").hide(500);
					$("#sezioneFormDateApertura").show(500);
				}
				
				
		        $("#date_apertura_form").submit(function(event) {
			        event.preventDefault();
			        event.stopPropagation();

			        //aggiungo la classe errore/successo di bootstrap
			        $("#date_apertura_form").addClass('was-validated');
			        
			        if(
		        		$("#dataAperturaInsert")[0].checkValidity() === true &&
		        		$("#postiDisponibiliInput")[0].checkValidity() === true
					) {

				        //aggiungo la classe errore/successo di bootstrap
				        $("#date_apertura_form").addClass('was-validated');
				        
				        inserisciDataApertura($("#centroVaccinaleDateApertura option:selected").val(), $("#dataAperturaInsert").val(), $("#postiDisponibiliInput").val());
			        }
				});
				
				
				
			}
		});
	}
	
	
	if($("#centroVaccinaleAddIcon").length){
		$("#centroVaccinaleAddIcon").click(function(){
			if($("#sezioneFormCentroVaccinaleAdd").length){
				//open form per inserire un nuovo centro vaccinale
				$("#sezioneAspSuccessResponse").empty();
				$("#sezioneAspSuccessResponse").hide();
				$("#sezioneApprovazioneFailResponse").empty();
				$("#sezioneApprovazioneFailResponse").hide();
				$("#sezioneFormAsp").hide();
				$("#sezioneFormDateApertura").hide();
				$("#denominazioneCentroVaccinaleAdd").val("");
        		$("#indirizzoCentroVaccinaleAdd").val("");
        		$(".HideInputDenominazione").hide();
        		$(".HideInputIndirizzo").hide();
				
				
				if($("#sezioneFormCentroVaccinaleAdd").is(":visible")){
					$("#sezioneFormCentroVaccinaleAdd").hide(500);
				}else if($("#sezioneFormCentroVaccinaleAdd").is(":hidden")){
					$("#sezioneFormDateApertura").hide(500);
					$("#sezioneFormAsp").hide(500);
					$("#sezioneFormCentroVaccinaleAdd").show(500);
				}
				
				
				loadSelectCentroVaccinaleAdd();

				$("#centro_vaccinale_add_form").submit(function(event) {
			        event.preventDefault();
			        event.stopPropagation();

			        //aggiungo la classe errore/successo di bootstrap
			        $("#centro_vaccinale_add_form").addClass('was-validated');
			        
			        if(
		        		$("#denominazioneCentroVaccinaleAdd")[0].checkValidity() === true &&
		        		$("#indirizzoCentroVaccinaleAdd")[0].checkValidity() === true
					) {
		        
				        inserisciCentroVaccinale(
				        		$("#regioneCentroVaccinaleAdd option:selected").val(), 
				        		$("#provinciaCentroVaccinaleAdd option:selected").val() , 
				        		$("#comuneCentroVaccinaleAdd option:selected").val(),
				        		$("#denominazioneCentroVaccinaleAdd").val(),
				        		$("#indirizzoCentroVaccinaleAdd").val()
				        );
			        }
				});
				
			}
		});
	}
	
	
	
	
	if($("#scaricaDatiIcon").length){
		$("#scaricaDatiIcon").click(function(){
			if($("#sezioneFormAsp").length){
				//open form per approvazione dipendenti
				$("#sezioneAspSuccessResponse").empty();
				$("#sezioneAspSuccessResponse").hide();
				$("#sezioneApprovazioneFailResponse").empty();
				$("#sezioneApprovazioneFailResponse").hide();
				$("#sezioneFormDateApertura").hide();
				
				loadSelectApprova(true);
				
				if($("#sezioneFormAsp").is(":visible")){
					$("#sezioneFormAsp").hide(500);
				}
				else if($("#sezioneFormAsp").is(":hidden")){
					$("#sezioneFormAsp").show(500);
				}
			}
		});
	}
	
	
	/**** END PAGINA ASP ****/
	
	
	/**** PAGINA MEDICO ****/
	
	if($("#somministraDipendenteIcon").length) {
		
		//open form per trovare utente con codice prenotazione
		$("#somministraDipendenteIcon").click(function(){
			$("#div_fail_insert_smnt").empty();
			$("#div_fail_insert_smnt").hide();
			$("#div_fail_insert_lista_utenti").empty();
			$("#div_fail_insert_lista_utenti").hide();
			$("#div_fail_smnt_lista_utenti").empty();
			$("#div_fail_smnt_lista_utenti").hide();
			$("#sezioneTrovaCodSuccessResponse").hide();
			
			$("#codiceUtenteTrovaCod").val("");
			
			$("#trovaCodDiv").removeClass('was-validated');
			
			if($("#sezioneTrovaCod").is(":visible")){
				$("#sezioneTrovaCod").hide(500);
			}
			else if($("#sezioneTrovaCod").is(":hidden")){
				$("#sezioneTrovaCod").show(500);
			}
		})
		
		//invia trova utenti
		$("#arrowCodiceUtenteTrovaCod").click(function(){
			trovaUtenteByCod();
		})
	}
	
		
		//open list utenti a turno
		$("#listaUtentiTurnoIcon").click(function(){
			$("#div_fail_insert_smnt").empty();
			$("#div_fail_insert_smnt").hide();
			$("#div_fail_insert_lista_utenti").empty();
			$("#div_fail_insert_lista_utenti").hide();
			$("#div_fail_smnt_lista_utenti").empty();
			$("#div_fail_smnt_lista_utenti").hide();
			$("#sezioneTrovaCod").hide();
			
			listaUtentiATurno("list_turno_by_id_utente");
			
		})
	
	/**** END PAGINA MEDICO ****/
	
	
	/**** LISTA TURNO ****/
		if($("#tabellone_turno").length){
			
			//se la richiesta proviene dall'area accettazione
			let searchParams = new URLSearchParams(window.location.search);
			if(searchParams.has('opAsp') && searchParams.get('opAsp') ==="true"){
				tabellone("tabellone_by_id_utente");
				
				$("#plus_numero_turno").click(function(){
					tabellone("tabellone_by_id_utente", "incrementa_turno");
				});
				
				$("#minus_numero_turno").click(function(){
					tabellone("tabellone_by_id_utente", "decrementa_turno");
				});
				return;
			}
			

			//se la richiesta proviene dalla pagina principale
			loadSelectTabellone();
			
		}
	
	
	
	/**** END LISTA TURNO ****/
	
});





/* FUNZIONI GENERICHE */

//loading spinner
function loadSpinner(close=false){
  if(close == false){
	$("#spinnerLoad").show();
  }else{
	$("#spinnerLoad").hide();
  }
}


//carica regioni da file json generico
function loadRegioniGenerico(ref, optionSelected=""){
	$(ref).empty();
	
	$.getJSON("/googlielmo93/js/json/comuni_ita.json")
	.done(function(data){
	    $(ref).prepend($('<option>').val("").text("Scegli una regione"));
	    
	    $.each(data.regioni, function (index, value) {
		    optText = value.nome;
		    optValue = value.nome;
		    if(value!=undefined && value!="" && value)
		    	$(ref).append($('<option>').val(optValue).text(optText));
		});
	    
	    if(optionSelected != "")
			$(ref+" option[value=\""+optionSelected+"\"]").prop('selected', true);
	})
	.fail(function(){
		$(ref).prepend($('<option>').val("").text("Nessuna regione presente"));
	});
}


//estrai province in base alla regione selezionata
function loadProvinceGenerico(ref, regione, optionSelected=""){
	$(ref).empty();
	
	$.getJSON("/googlielmo93/js/json/comuni_ita.json")
	.done(function(data){
		$.each(data.regioni, function (index, value) {
	    	if(value.nome == regione){
	    		$(ref).prepend($('<option>').val("").text("Scegli una provincia"));
	    		
	    		let province_exists = 0;
	    		$.each(value.province, function (indexProv, valueProv) {
				    optText = valueProv.nome;
				    optValue = valueProv.nome;
				    if(valueProv!=undefined && valueProv!="" && valueProv){
				    	$(ref).append($('<option>').val(optValue).text(optText));
				    	province_exists = 1;
				    }
	    		});
	    		
	    		if(province_exists == 0)
	    			$(ref).prepend($('<option>').val("").text("Nessuna provincia presente per la regione selezionata"));
	    		else if(optionSelected != "")
	    			$(ref+" option[value=\""+optionSelected+"\"]").prop('selected', true);
	    	}
		}); 
        
    })
    .fail(function(){
		$(ref).prepend($('<option>').val("").text("Nessuna provincia presente"));
    });
	
}


//estrai comuni in base alla provincia selezionata
function loadComuniGenerico(ref,regione, provincia, optionSelected="") {
	$(ref).empty();
	
	$.getJSON("/googlielmo93/js/json/comuni_ita.json")
	.done(function(data){
		$.each(data.regioni, function (index, value) {
	    	if(value.nome == regione){
	    		let province_exists = 0, comuni_exists = 0;
				$.each(value.province, function (indexProv, valueProv) {
	    			if(valueProv.nome == provincia){
	    				province_exists = 1;
	    				$(ref).prepend($('<option>').val("").text("Scegli un comune"));
	    				
			    		$.each(valueProv.comuni, function (index, valueComune) {
			    			comuni_exists = 1;
						    optText = valueComune.nome;
						    optValue = valueComune.nome;
						    if(valueComune!=undefined && valueComune!="" && valueComune)
						    	$(ref).append($('<option>').val(optValue).text(optText));
			    		});
			    	}
	    		});
				
	    		if(province_exists == 0)
	    			$(ref).prepend($('<option>').val("").text("Nessuna provincia presente per la regione selezionata"));
	    		
	    		if(comuni_exists == 0)
	    			$(ref).prepend($('<option>').val("").text("Nessun comune presente per la provincia selezionata"));
	    		else if(optionSelected != "")
	    			$(ref+" option[value=\""+optionSelected+"\"]").prop('selected', true);

	    	}
		}); 
        
    })
    .fail(function(){
		$(ref).prepend($('<option>').val("").text("Nessuna comune presente"));
    });
}


//Fa una chiamata ajax col metodo jquery post alla servlet CentroVaccinaleServlet
function loadListCentriVaccinaliGenerico(ref, regione, provincia, comune = ""){

	$(ref).empty();
	
	let dataForm = {
			operation : "listCentroVaccinale",
	    	regione : regione,
			provincia : provincia
    };
	
	
	if(comune && comune != ""){
		let dataFormUser = 
		{
			comune : comune
		};
		
		Object.assign(dataForm, dataFormUser)
	}
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post(
		"/googlielmo93/centroVaccinale", dataForm, () =>{loadSpinner()}
	)
	.done(
		function(data) {
			loadSpinner(true);
			
			$(ref).empty();
			
			$(ref).prepend($('<option>').val("").text("Scegli un centro vaccinale"));
			
			let centro_vaccinale_exists = 0;
			
			$.each(data.centri_vaccinali, function (index, value) {
			    
			    optText = value.denominazione;
			    optValue = value.id_centro_vaccinale;
			    
			    if(optValue!=undefined && optValue!="" && optValue){
			    	$(ref).append($('<option>').val(optValue).text(optText));
			    	centro_vaccinale_exists = 1;
			    }
			});
    		
    		if(centro_vaccinale_exists == 0){
    			$(ref).empty();
    			$(ref).prepend($('<option>').val("").text("Nessun centro vaccinale disponibile nella zona"));
    		}
	})
	.fail(function(){
		$(ref).empty();
		$(ref).prepend($('<option>').val("").text("Nessun centro vaccinale disponibile nella zona"));
    });
	
}


/* END FUNZIONI GENERICHE */



/* INIZIO FUNZIONI UTENTE */

//carica regioni da file json per prenotazione utente
function loadRegioni(){
	$.getJSON("/googlielmo93/js/json/comuni_ita.json")
	.done(function(data){
	    $("#regionePrenotazione").prepend($('<option>').val("").text("Scegli una regione"));
	    
	    $.each(data.regioni, function (index, value) {
		    optText = value.nome;
		    optValue = value.nome;
		    if(value!=undefined && value!="" && value)
		    	$("#regionePrenotazione").append($('<option>').val(optValue).text(optText));
		});
	})
	.fail(function(){
		$("#regionePrenotazione").prepend($('<option>').val("").text("Nessuna regione presente"));
	});
}



//estrai province in base alla regione selezionata per prenotazione utente
function loadProvince(regione){
	
	$.getJSON("/googlielmo93/js/json/comuni_ita.json")
	.done(function(data){
		$.each(data.regioni, function (index, value) {
	    	if(value.nome == regione){
	    		$("#provinciaPrenotazione").prepend($('<option>').val("").text("Scegli una provincia"));
	    		
	    		let province_exists = 0;
	    		$.each(value.province, function (indexProv, valueProv) {
				    optText = valueProv.nome;
				    optValue = valueProv.nome;
				    if(valueProv!=undefined && valueProv!="" && valueProv){
				    	$("#provinciaPrenotazione").append($('<option>').val(optValue).text(optText));
				    	province_exists = 1;
				    }
	    		});
	    		
	    		if(province_exists == 0)
	    			$("#provinciaPrenotazione").prepend($('<option>').val("").text("Nessuna provincia presente per la regione selezionata"));
	    	}
		}); 
        
    })
    .fail(function(){
		$("#provinciaPrenotazione").prepend($('<option>').val("").text("Nessuna provincia presente"));
    });
	
}



//estrai comuni in base alla provincia selezionata per prenotazione utente
function loadComuni(regione, provincia) {
	
	$.getJSON("/googlielmo93/js/json/comuni_ita.json")
	.done(function(data){
		$.each(data.regioni, function (index, value) {
	    	if(value.nome == regione){
	    		let province_exists = 0, comuni_exists = 0;
				$.each(value.province, function (indexProv, valueProv) {
	    			if(valueProv.nome == provincia){
	    				province_exists = 1;
	    				$("#comunePrenotazione").prepend($('<option>').val("").text("Scegli un comune"));
	    				
			    		$.each(valueProv.comuni, function (index, valueComune) {
			    			comuni_exists = 1;
						    optText = valueComune.nome;
						    optValue = valueComune.nome;
						    if(valueComune!=undefined && valueComune!="" && valueComune)
						    	$("#comunePrenotazione").append($('<option>').val(optValue).text(optText));
			    		});
			    	}
	    		});
				
	    		if(province_exists == 0)
	    			$("#provinciaPrenotazione").prepend($('<option>').val("").text("Nessuna provincia presente per la regione selezionata"));
	    		
	    		if(comuni_exists == 0)
	    			$("#comunePrenotazione").prepend($('<option>').val("").text("Nessun comune presente per la provincia selezionata"));

	    	}
		}); 
        
    })
    .fail(function(){
		$("#comunePrenotazione").prepend($('<option>').val("").text("Nessuna comune presente"));
    });
}



// Fa una chiamata ajax col metodo jquery post alla servlet CentroVaccinaleServlet
function loadListCentriVaccinali(regione, provincia, comune = ""){

	$("#centroVaccinalePrenotazione").empty();
	$("#datePrenotazione").empty();
	$("#div_fail_insert").empty();
	$("#div_fail_insert").hide();
	
	let dataForm = {
			operation : "listCentroVaccinale",
	    	regione : regione,
			provincia : provincia
    };
	
	
	if(comune && comune != ""){
		let dataFormUser = 
		{
			comune : comune
		};
		
		Object.assign(dataForm, dataFormUser)
	}
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post("/googlielmo93/centroVaccinale", dataForm, () =>{loadSpinner()})
	.done(
		function(data) {
			loadSpinner(true);
			
			$("#centroVaccinalePrenotazione").empty();
			$("#div_fail_insert").empty();
			$("#div_fail_insert").hide();
			$("#centroVaccinalePrenotazione").prepend($('<option>').val("").text("Scegli un centro vaccinale"));
			
			let centro_vaccinale_exists = 0;
			
			$.each(data.centri_vaccinali, function (index, value) {
			    
			    optText = value.denominazione;
			    optValue = value.id_centro_vaccinale;
			    
			    if(optValue!=undefined && optValue!="" && optValue){
			    	$("#centroVaccinalePrenotazione").append($('<option>').val(optValue).text(optText));
			    	centro_vaccinale_exists = 1;
			    }
			});
    		
    		if(centro_vaccinale_exists == 0){
    			$("#centroVaccinalePrenotazione").empty();
    			$("#centroVaccinalePrenotazione").prepend($('<option>').val("").text("Nessun centro vaccinale disponibile nella zona"));
    		}
	})
	.fail(function(){
		$("#centroVaccinalePrenotazione").empty();
		$("#centroVaccinalePrenotazione").prepend($('<option>').val("").text("Nessun centro vaccinale disponibile nella zona"));
    });
	
	
}



//carica le date disponibili per prenotare in base al centro vaccinale specificato, facendo una chiamata ajax alla servlet centroVaccinale,
//con parametro operation = listDateCentroVaccinale
function loadDateDisponibiliPrenotazione(id_centro_vaccinale){
	
	let dataForm = {
			operation : "listDateCentroVaccinale",
			id_centro_vaccinale : id_centro_vaccinale
	};

	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post("/googlielmo93/centroVaccinale", dataForm, () =>{loadSpinner()})
	.done(
		function(data) {
			loadSpinner(true);
			$(".HideInputDateDisponibiliPrenotazione").show(500);
			
			postiNum = Object.keys(data.posti).length;
			if(postiNum !== 0){
					
				let str="";
				
				$.each(data.posti, function (index, value) {
				    
				    posti_disponibili = value.posti_disponibili;
				    data_disponibile = value.data;
				    
				    if(posti_disponibili!=undefined && posti_disponibili!="" && posti_disponibili){
				    		
				    	str += "<div id=\"data_disp_"+value.id_giorno_apertura+"\" class=\"card border-info m-2 card_mod\" style=\"width: 14rem; font-size:.92rem; cursor: pointer;";
				    			if(index >= 6)
				    				str += "display : none!important;";
				    			str += "\"" +
				    				" onclick = ' prenotaVaccino("+value.id_giorno_apertura+"); ' >" +
				    				"<div class=\"card-body\">" +
				    					"<h5 class=\"card-title\">" +
				    					data_disponibile +
				    					"<br><br>Posti Disponibili:<br>" +
				    						 value.posti_disponibili +
				    					"</h5>" +
				    					" <ul class=\"list-group\">" +
						    					"<li class=\"list-group-item\">" + value.denominazione + "</li>" +
						    					"<li class=\"list-group-item\">" + value.comune + "</li>" +
						    					"<li class=\"list-group-item\">" + value.indirizzo + "</li>" +
						    			" </ul> " +
				    				"</div>" +
				    			"</div>";
				    }
					 
				});
				

				if(postiNum > 6){
					str += "<div class=\"col-md-10 my-5 text-center\" style=\"cursor:pointer\" id=\"buttonVediAltri\"><h5>Vedi Altri</h5></div>";
				}
				
				$("#datePrenotazione").append(str + "</div>");
				
				if(postiNum > 6){
					
					$("#buttonVediAltri").click(
						function(){
							let count = 0;
							$("#datePrenotazione").find(".card").each(
								(i, el) => {
									if($(el).is(":hidden")){
										$("#buttonVediAltri").hide();
										
										if(count >= 6){
											$("#buttonVediAltri").show();
											return;
										}
									
										count++;
										$(el).show();
									}
								}
							);
							
						}
					);
				}
				
				
			}else{
				let str = "<div id=\"data_non_disp\" class=\"card border-info m-2 mx-auto\" style=\"width: 100%; font-size:.92rem;\" >" +
	    				"<div class=\"card-body\">" +
	    					"<h5 class=\"card-title\">Nessuna data disponibile per prenotare il vaccino. Si prega di selezionare un'altro centro vaccinale o di contattarci.</h5>" +
	    				"</div>" +
	    			"</div>";
					
					$("#datePrenotazione").append(str);
			}
			
	})
	.fail(function(){
		$("#datePrenotazione").append("<div class=\"p-2 alert alert-danger alert-dismissible fade show\">Nessun giorno disponibile per il centro vaccinale selezionato! La preghiamo di provare pi&ugrave; tardi o di contattarci.</div>");
});
}



function prenotaVaccino(id){
	
	let dataForm = {
		id_giorno_apertura : id
	};

	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post(
		"/googlielmo93/prenotazione_vaccino", dataForm, () =>{loadSpinner()}
	)
	.done(
		function(data) {
			loadSpinner(true);
			
			if(Object.keys(data.result).length){
				
				if(data.result == "success"){
					
					let str =
					"<div id=\"success_insert\" class=\"card border-success m-2 mb-5 mx-auto alert alert-success\" style=\"width: 100%; font-size:.92rem;\" >" +
						"<div class=\"card-body\">" +
							"<h5 class=\"card-title\">Prenotazione avvenuta con successo!</h5>" +
							"<h6>Si prega di recarsi nel centro vaccinale selezionato nella data di prenotazione effettuata,<br>ricever&agrave; ulteriori informazioni in loco.</h6>" +
						"</div>" +
					"</div>";
					
					$("#sezionePrenotazione").prepend(str);
					
					if($("#sezionePrenotazione").length){
						
						$("#sezioneFormPrenotazione").hide(500);
						$("#iconFormPrenotazione").hide(500);
						
						loadInfoPrenotazione();
					}
					
				}else{
					
					$("#div_fail_insert").empty();
					let str = 
					"<br><br><div id=\"fail_insert\" class=\"card border-danger m-2 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
						"<div class=\"card-body\">" +
							"<h5 class=\"card-title\">Prenotazione non andata a buon fine!</h5>" +
							"<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
						"</div>" +
					"</div>";
					
					$("#div_fail_insert").append(str);
			
					$("#div_fail_insert").show(500);
				}
			}
			
	})
	.fail(function(){
		$("#datePrenotazione").append("<div class=\"p-2 alert alert-danger alert-dismissible fade show\">Nessun giorno disponibile per il centro vaccinale selezionato! La preghiamo di provare pi&ugrave; tardi o di contattarci.</div>");
	});
}



function creaQrCode(text){
	//creo il QR code usando il metodo della libreria jquery-qrcode.min.js
	$("#qrcode_vaccinazione").qrcode(
		{
		    render:"table",
		    width: 200,
		    height: 200,
		    text: text
		}
	);
}



// verifico se è stato somministrato il vaccino, scarico i dati dalla tabella somministrazione
// creo il QRCODE relativo alla somministrazione con l'ausilio della libreria jquery
function loadInfoSomministrazione(){
	
	//apri info dati somministrazione
	if ($("#sezioneSomministrazione").length) {
		
		$("#valueQrCode").empty();
		$("#list_somministrazioni").empty();
		
		//dataForm = PlainObject (JS Obj)
		//$.post accetta o PlainObject o Stringhe non JSON String
		$.post(
			"/googlielmo93/recupera_somministrazione", () =>{loadSpinner()}
		)
		.done(
			function(data) {
				loadSpinner(true);
				
				let smntLenght = data.somministrazioni.length;
				
				$("#count_somministrati").text("Totale "+smntLenght);
				
				if(Object.keys(data.somministrazioni).length > 0){
					
					//sezione info somministrazioni
					$.each(data.somministrazioni, function (index, value) {
					    
					    let smnt = value;
					    
					    if(smnt!=undefined && smnt!="" && smnt){
					    	
					    	let str = "", info_smnt = smnt.info_somministrazione, info_cntr = smnt.info_centro_vaccinale;
					    	

							if(index == 0){
						    	//sezione QrCode
								let infoUserSession = $("#valueQrCode").val();
								let textQrCode = "codice:"+ info_smnt.codice_vaccino +"lotto:"+ info_smnt.lotto + infoUserSession;
								
								$("#openInfoQr").click( ()=> {
									$("#arrowInfoIconQr").remove();
									
									if($("#divInfoQr").is(":visible")){
										$("#divInfoQr").hide(500);
										$("#codInfoQr").append("<i id=\"arrowInfoIconQr\" class=\"fas fa-angle-down centraverticale\" style=\"color: #17a2b8!important; margin-left: 20%!important;\" aria-hidden=\"true\"></i>")
										return;
									}
									
									if($("#divInfoQr").is(":hidden")){
										$("#divInfoQr").show(500);
										$("#codInfoQr").append("<i id=\"arrowInfoIconQr\" class=\"fas fa-angle-up centraverticale\" style=\"color: #17a2b8!important; margin-left: 20%!important;\" aria-hidden=\"true\"></i>")
										return;
									}
								});
								
								creaQrCode(textQrCode);
					    	}
							//fine sezione QrCode
							
							str += "<div class=\"row\">";
								str += "<div class=\"col-md-12 pb-5 ml-3\">";
						    	
						    	if(Object.keys(info_cntr).length !== 0){ 
						    		str += "<div class=\" col-md-6 mt-5 align-middle text-left\" style=\"display:inline-block!important;\">";
				    			} else {
				    				str += "<div class=\" col-md-12 mt-5 align-middle text-left\" style=\"display:inline-block!important;\">";
				    			}
								    	str += "<label for=\"infoQrDati\" class=\"font-weight-bold\">Info Somministrazione</label>" +
								    			" <ul class=\"list-group\">" +
								    					"<li class=\"list-group-item\">Tipo Vaccino: " + info_smnt.tipo_vaccino + "</li>" +
								    					"<li class=\"list-group-item\">Codice Vaccino: " + info_smnt.codice_vaccino + "</li>" +
								    					"<li class=\"list-group-item\">Lotto: " + info_smnt.lotto + "</li>" +
								    					"<li class=\"list-group-item\">Codice prenotazione associato: " + info_smnt.codice_prenotazione + "</li>" +
								    					"<li class=\"list-group-item\">Data vaccinazione: " + info_smnt.data_somministrazione + "</li>" +
								    					"<li class=\"list-group-item\">Codice Medico: " + info_smnt.id_medico + "</li>" +
								    			" </ul> "+
							    			"</div>";
								
						    	
						    	if(Object.keys(info_cntr).length !== 0){
								    str +=  "<div class=\" col-md-6 mt-5 align-middle text-left float-right\" style=\"display:inline-block!important;\">" +
												"<label for=\"infoPrenotazione\" class=\"font-weight-bold\">Info Centro Vaccinale</label>" +
								    			" <ul class=\"list-group\">" +
								    					"<li class=\"list-group-item\">Denominazione: " + info_cntr.denominazione + "</li>" +
								    					"<li class=\"list-group-item\">Comune: " + info_cntr.comune + "</li>" +
								    					"<li class=\"list-group-item\">Provincia: " + info_cntr.provincia + "</li>" +
								    					"<li class=\"list-group-item\">Regione: " + info_cntr.regione + "</li>" +
								    					"<li class=\"list-group-item\">Indirizzo: " + info_cntr.indirizzo + "</li>" +
								    			" </ul> " +
							    			"</div>";
						    	}
							    
						    	str += "</div>";
					    	str += "</div>";
	
					    	$("#list_somministrazioni").append(str);
			    	
					    }
					});
				
					$("#sezioneSomministrazione").show(500);
					
				}
				
				let completeVacc = false;
				
				// se ci sono altre somministrazioni rimanenti, quindi il ciclo di vaccinazioni non e' stato completato ritorna false, altrimenti true
				if(Object.keys(data.totale_somministrazioni_rimanenti).length > 0){
					completeVacc = ( ( data.totale_somministrazioni_rimanenti > 0 ) ? false : true );
				}
	    		

				//caricamento sezione prenotazione
				if($("#sezionePrenotazione").length && completeVacc == false ){  //completeVacc false, allora c'è almeno una smnt da fare
					$("#sezionePrenotazione").hide();
					loadInfoPrenotazione();
				}else{
					
					let str = "<div id=\"complete_smnt\" class=\"card border-info m-2 mx-auto alert alert-info\" style=\"width: 100%; font-size:.92rem;\" >" +
								"<div class=\"card-body\">" +
									"<h5 class=\"card-title\">Hai completato il ciclo vaccinale per adesso!</h5>" +
									"<h6>Il ciclo successivo di vaccinazioni verr&agrave; comunicato medico curante quando sar&agrave; disponibile.<br>" +
									"Per ulteriori informazioni si prega di consultare il sito ministeriale e i relativi D.P.C.R. relativi all'ambito sanitario.</h6>" +
								"</div>" +
							"</div>";
					

					$("#sezionePrenotazione").empty();
					
					$("#sezionePrenotazione").append(str);
			
					$("#sezionePrenotazione").show(500);
				}
				
		})
		.fail(function(){
			$("#valueQrCode").empty();
			$("#list_somministrazioni").empty();
			
			let str = "<div id=\"errore_load_info_smnt\" class=\"card border-info m-2 mx-auto alert alert-warning\" style=\"width: 100%; font-size:.92rem;\" >" +
						"<div class=\"card-body\">" +
							"<h5 class=\"card-title\">Non &egrave; stato possibile portare a termine le operazioni, al momento il servizio non &egrave; disponibile.<br>" +
							"Si prega di provare a ricaricare la pagina o a effettuare di nuovo il login.<br>" +
							"Se il problema persiste, la preghiamo di contattarci.</h5>" +
						"</div>" +
					"</div>";
			
			$("#list_somministrazioni").append(str);
			
	    });
		
	}
	
	return false;
	
}


function eliminaPrenotazione(codice){
	
	$("#error_delete").hide();
	
	let dataForm = {
		operation : "delete_prenotazione",
		codice : codice
	};

	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post(
		"/googlielmo93/elimina_prenotazione", dataForm, () =>{loadSpinner()}
	)
	.done(
		function(data) {
			loadSpinner(true);
			
			if(Object.keys(data.result).length && data.result == "success"){
				$("#sezionePrenotazione").hide(500);
				$("#iconFormPrenotazione").show(500);
				
			}else{
				$("#error_delete").show(500);
				$("#error_delete").append("<h5>"+data.message+"</h5>");
			}
	})
	.fail(function(){
		$("#error_delete").show(500);
    });
							
}


function loadInfoPrenotazione(){
	
	//apri info dati prenotazione
	if ($("#sezionePrenotazione").length) {
		
		$("#list_prenotazioni").empty();
		
		//dataForm = PlainObject (JS Obj)
		//$.post accetta o PlainObject o Stringhe non JSON String
		$.post(
			"/googlielmo93/recupera_prenotazione", () =>{loadSpinner()}
		)
		.done(
			function(data) {
				
				loadSpinner(true);
				
				// SE NON C'è NESSUNA PRENOTAZIONE ATTIVA, UNA PER VOLTA, VERRA' VISUALIZZATO IL FORM PER L'INSERIMENTO
				// ALTRIMENTI VIENE VISUALIZZATO IL RIEPILOGO DEI DATI DELLA PRENOTAZIONE EFFETTUATA
				
				if(Object.keys(data.prenotazioni).length > 0){
					
					//sezione info prenotazioni
					$.each(data.prenotazioni, function (index, value) {
						
					    let prnt = value;
					    
					    if(prnt!=undefined && prnt!="" && prnt){
					    	let str = "", info_prnt = prnt.info_prenotazione, info_cntr = prnt.info_centro_vaccinale;
					    	
							$("#codice_prenotazione_attivo").text(info_prnt.codice_prenotazione);
							
							str += "<div class=\"row\">";
								str += "<div class=\"col-md-12 pb-5 ml-3\">";
						    	
						    	if(Object.keys(info_cntr).length !== 0){ 
						    		str += "<div class=\" col-md-6 mt-5 align-middle text-left\" style=\"display:inline-block!important;\">";
				    			} else {
				    				str += "<div class=\" col-md-12 mt-5 align-middle text-left\" style=\"display:inline-block!important;\">";
				    			}
								    	str += "<label for=\"infoQrDati\" class=\"font-weight-bold\">Info Prenotazione</label>" +
								    			" <ul class=\"list-group\">" +
						    							"<li class=\"list-group-item\">Codice prenotazione: " + info_prnt.codice_prenotazione + "</li>" +
								    					"<li class=\"list-group-item\">Data prenotazione: " + info_prnt.data_prenotazione + "</li>" +
								    					"<li class=\"list-group-item\">Data di inserimento prenotazione: " + info_prnt.data_inserimento + "</li>" +
								    			" </ul> " +
							    			"</div>";

							    	
						    	if(Object.keys(info_cntr).length !== 0){
						    	
								    str +=  "<div  id=\"div_prenotazione_"+index+"\" class=\"col-md-6 mt-5 align-middle text-left float-right\" style=\"display:inline-block!important;\">" +
								    			"<input type=\"hidden\" value=\""+info_prnt.codice_prenotazione+"\" id=\"codice_prenotazione_hidden\">" +
												"<label for=\"infoPrenotazione\" class=\"font-weight-bold\">Info Centro Vaccinale</label>" +
								    			" <ul class=\"list-group\">" +
								    					"<li class=\"list-group-item\">Denominazione: " + info_cntr.denominazione + "</li>" +
								    					"<li class=\"list-group-item\">Comune: " + info_cntr.comune + "</li>" +
								    					"<li class=\"list-group-item\">Provincia: " + info_cntr.provincia + "</li>" +
								    					"<li class=\"list-group-item\">Regione: " + info_cntr.regione + "</li>" +
								    					"<li class=\"list-group-item\">Indirizzo: " + info_cntr.indirizzo + "</li>" +
								    			" </ul> " +
							    			"</div>";
								    
						    	}
						    	
							    
							    str += "</div>";
						    str += "</div>";
						    
						   /* 
						    if(Object.keys(info_cntr).length !== 0){
						    	str += "<div id=\"error_delete\" style=\"display:none\">Errore durante la cancellazione!</div>" +
						    	"<button type=\"button\" class=\"mt-5 btn btn-primary btn-lg btn-block\" id=\"closeDiv_"+index+"\">Elimina Prenotazione</button>" ;
						    	

						    	$("#list_prenotazioni").append(str);
						    	str =""
								
								if($("#div_prenotazione_"+index).length){
									$("#closeDiv_"+index).click(function(){
										eliminaPrenotazione( info_prnt.codice_prenotazione);
									})
								}
						    }
						    */
						    
						    $("#list_prenotazioni").append(str);
			    	
					    }
					});
					
					$("#sezionePrenotazione").show(500);
					
				}else{
					
					//INIZIO SEZIONE PRENOTAZIONE FORM
					//se esiste il tag con id regionePrenotazione
					if ($("#sezioneFormPrenotazione").length) {
						
						$("#sezioneIcon").show(500);
						$("#iconFormPrenotazione").show();
						
						$("#sezioneFormPrenotazione").hide();
						
						$("#regionePrenotazione").empty();
						$("#provinciaPrenotazione").empty();
						$("#comunePrenotazione").empty();
						$("#datePrenotazione").empty();
						$("#div_fail_insert").empty();
						$("#div_fail_insert").hide();
						
						$(".HideInputProvincia").hide();
						$(".HideInputComune").hide();
						$(".HideInputCentroVaccinale").hide();
						$(".HideInputDateDisponibiliPrenotazione").hide();
						
						//carico le regioni
						loadRegioni();
						
						
						
						// al change sulla select regione fa il load delle corrispettive province nella select province
						$("#regionePrenotazione").change(function(){
							
							$("#provinciaPrenotazione").empty();
							$("#comunePrenotazione").empty();
							$("#datePrenotazione").empty();
							$("#centroVaccinalePrenotazione").empty();
							$("#text-scelta-vaccinazione").empty();
							$("#div_fail_insert").empty();
							$("#div_fail_insert").hide();
							
							//nascondi
							$(".HideInputComune").hide();
							$(".HideInputCentroVaccinale").hide();
							$(".HideInputDateDisponibiliPrenotazione").hide();
							
							//mostra la select provincia
							$(".HideInputProvincia").show(500);
						    $("#text-scelta-vaccinazione").text("Seleziona la provincia");
						    
						    //carica province in base alla regione passata
						    loadProvince($("#regionePrenotazione option:selected").val());
						    
						});

						
						
						// al change sulla select province fa il load sulla select dei comuni corrispondenti alla provincia,
						// e inoltre fa il load dei centri vaccinali nella provincia
						$("#provinciaPrenotazione").change(function(){
							$("#comunePrenotazione").empty();
							$("#centroVaccinalePrenotazione").empty();
							$("#datePrenotazione").empty();
							$("#text-scelta-vaccinazione").empty();
							$("#div_fail_insert").empty();
							$("#div_fail_insert").hide();

							//mostra
							$(".HideInputComune").show(500);
							$(".HideInputCentroVaccinale").show(500);
						    $("#text-scelta-vaccinazione").text("Seleziona il centro vaccinale o specifica il comune");
						    
						    // carico i centri vaccinali in base alla regione, alla provincia
						    loadListCentriVaccinali($("#regionePrenotazione option:selected").val(), $("#provinciaPrenotazione option:selected").val());
						    
						    // carico i comuni in base alla regione e alla provincia selezionata
							loadComuni($("#regionePrenotazione option:selected").val(), $("#provinciaPrenotazione option:selected").val())
						})

						
						// al change sulla select comune fa il trigger sulla select dei centri vaccinali nel comune
						$("#comunePrenotazione").change(function(){
							$("#centroVaccinalePrenotazione").empty();
							$("#datePrenotazione").empty();
							$("#text-scelta-vaccinazione").empty();
							$("#div_fail_insert").empty();
							$("#div_fail_insert").hide();
							
							//nascondi
							$(".HideInputDateDisponibiliPrenotazione").hide();
							
							//mostra
							$(".HideInputCentroVaccinale").show(500);
						    $("#text-scelta-vaccinazione").text("Seleziona il centro vaccinale");
						    
						    // carico i centri vaccinali in base alla regione, alla provincia e il comune
						    loadListCentriVaccinali($("#regionePrenotazione option:selected").val(), $("#provinciaPrenotazione option:selected").val(), $("#comunePrenotazione option:selected").val());
						
						})
						
						
						
						// al change sulla select centro vaccinale
						$("#centroVaccinalePrenotazione").change(function(){
							$("#datePrenotazione").empty();
							$("#text-scelta-vaccinazione").empty();
							$("#div_fail_insert").empty();
							$("#div_fail_insert").hide();

							//nascondi
							$(".HideInputDateDisponibiliPrenotazione").hide();
						    $("#text-scelta-vaccinazione").text("Scegli una data tra quelle disponibili");
						    
						    let centroVaccId = $("#centroVaccinalePrenotazione option:selected").val();
						    
						    if(centroVaccId && centroVaccId != "" && centroVaccId != -1){
						    	// carica le date disponibili relative al centro vaccinale selezionato
						    	loadDateDisponibiliPrenotazione($("#centroVaccinalePrenotazione option:selected").val());
						    	
						    }else{
								$(".HideInputDateDisponibiliPrenotazione").hide();
						    }
						    
						    
						});
						
						//FINE SEZIONE PRENOTAZIONE
					    
					}
					
					
					$("#sezioneFormPrenotazione").show();
					$("#divInfoPrenotazione").show();
					
				}
				
				
				if(Object.keys(data.storico).length > 0){
					
					$("#sezioneIcon").show(500);
					$("#iconArchivioPrenotazione").show();

					
					//apri info dati prenotazione archivio
					if ($("#openInfoArchivio").length) {
						
						$("#openInfoArchivio").click( ()=> {
							
							if($("#divInfoPrenotazioneArchivio").is(":visible")){
								$("#divInfoPrenotazioneArchivio").hide(500);
							}
							else if($("#divInfoPrenotazioneArchivio").is(":hidden")){
								
								if($("#divPrenotazione").is(":visible")){
									$("#divPrenotazione").hide(500);
								}
								
								$("#divInfoPrenotazioneArchivio").show(500);
							}
						});
					}
					
					//sezione info prenotazioni storico
					$.each(data.storico, function (index, value) {
						
					    let prnt = value;
					    
					    if(prnt!=undefined && prnt!="" && prnt){
					    	let str = "", info_prnt = prnt.info_prenotazione, info_cntr = prnt.info_centro_vaccinale;
					    	str += "<div class=\"row\">";
								str += "<div class=\"col-md-12 pb-5 ml-3\">";
						    	
						    	if(Object.keys(info_cntr).length !== 0){ 
						    		str += "<div class=\" col-md-6 mt-5 align-middle text-left\" style=\"display:inline-block!important;\">";
				    			} else {
				    				str += "<div class=\" col-md-12 mt-5 align-middle text-left\" style=\"display:inline-block!important;\">";
				    			}
								    	str += "<label for=\"infoQrDati\" class=\"font-weight-bold\">Info Prenotazione</label>" +
								    			" <ul class=\"list-group\">" +
								    					"<li class=\"list-group-item\">Codice prenotazione: " + info_prnt.codice_prenotazione + "</li>" +
								    					"<li class=\"list-group-item\">Data prenotazione: " + info_prnt.data_prenotazione + "</li>" +
								    					"<li class=\"list-group-item\">Data di inserimento prenotazione: " + info_prnt.data_inserimento + "</li>" +
								    			" </ul> " +
							    			"</div>";
							    	
							    	
						    	if(Object.keys(info_cntr).length !== 0){
						    	
								    str +=  "<div class=\" col-md-6 mt-5 align-middle text-left float-right\" style=\"display:inline-block!important;\">" +
												"<label for=\"infoPrenotazione\" class=\"font-weight-bold\">Info Centro Vaccinale</label>" +
								    			" <ul class=\"list-group\">" +
								    					"<li class=\"list-group-item\">Denominazione: " + info_cntr.denominazione + "</li>" +
								    					"<li class=\"list-group-item\">Comune: " + info_cntr.comune + "</li>" +
								    					"<li class=\"list-group-item\">Provincia: " + info_cntr.provincia + "</li>" +
								    					"<li class=\"list-group-item\">Regione: " + info_cntr.regione + "</li>" +
								    					"<li class=\"list-group-item\">Indirizzo: " + info_cntr.indirizzo + "</li>" +
								    			" </ul> " +
							    			"</div>";
						    	}
						    	
							    
							    str += "</div>";
						    str += "</div>";
						    
						    
					    	$("#list_prenotazioni_archivio").append(str);
			    	
					    }
					});
					
					$("#sezionePrenotazioneArchivio").show(500);
					
				}
	    		
		})
		.fail(function(){
			$("#list_prenotazioni").empty();
	    });
		
	}
	
}

/* FINE FUNZIONI UTENTE */


/* INIZIO FUNZIONI ACCETTAZIONE */

function mettiATurno(){
	
	$("#sezioneMettiATurnoSuccessResponse").empty();
	$("#sezioneMettiATurnoSuccessResponse").hide();
	$("#div_fail_insert_turno").empty();
	$("#div_fail_insert_turno").hide();
	
	let codiceUtente = $("#codiceUtenteTurno").val();
	
	if(codiceUtente=="" || !codiceUtente){
		event.preventDefault();
        event.stopPropagation();

		//aggiungo la classe errore/successo di bootstrap
		$("#mettiATurnoDiv").addClass('was-validated');
		return;
	}else{
		$("#mettiATurnoDiv").removeClass('was-validated');
	}
	
	let dataForm = {
		codiceUtenteTurno : codiceUtente
    };
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post("/googlielmo93/InserisciATurnoServlet", dataForm, () =>{loadSpinner()})
	.done(
		function(data) {
			loadSpinner(true);
			
			if(Object.keys(data.result).length){
				
				if(data.result == "success"){
					
					let str =
					"<div id=\"success_insert_turno\" class=\"card border-success m-2 mb-5 mx-auto alert alert-success\" style=\"width: 100%; font-size:.92rem;\" >" +
						"<div class=\"row\">" +
							"<div class=\"col-sm-11\"></div>" +
							"<button type=\"button\" id=\"closeAlertMsgTurno\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
						"</div>" +
						"<div class=\"card-body\">" +
							"<h5 class=\"card-title\">Inserimento in coda avvenuto con successo!</h5>"+
						"</div>" +
					"</div>";
					
					//close alert message
					if ($("#success_insert_turno").length) { 
				 		$("button #closeAlertMsgTurno").click( ()=> {
							$("#success_insert_turno").fadeOut(500);
						});
					}
					
					$("#sezioneMettiATurno").hide(500);
					
					$("#sezioneMettiATurnoSuccessResponse").append(str);
					$("#sezioneMettiATurnoSuccessResponse").show(500);
					
				}else{
					
					let str = 
					"<br><br><div id=\"fail_insert_turno\" class=\"card border-danger m-2 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
						"<div class=\"card-body\">" +
							"<h5  style=\"font-weight: bold;\" class=\"card-title\">Inserimento in coda non andato a buon fine!</h5>";
							if(Object.keys(data.message).length){
								str += "<h6 style=\"font-weight: bold;\">"+data.message+"</h6><br>";
							}
							str += "<h6>Si prega di inserire un codice prenotazione valido per la giornata corrente,<br> se il problema sussiste la preghiamo di contattarci.</h6>" +
						"</div>" +
					"</div>";
					
					$("#div_fail_insert_turno").append(str);
			
					$("#div_fail_insert_turno").show(500);
				}
			}
	})
	.fail(function(){
			let str = 
			"<br><br><div id=\"fail_insert_turno\" class=\"card border-danger m-2 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
				"<div class=\"card-body\">" +
					"<h5  style=\"font-weight: bold;\" class=\"card-title\">Inserimento in coda non andato a buon fine!</h5>";
					if(Object.keys(data.message).length){
						str += "<h6 style=\"font-weight: bold;\">"+data.message+"</h6><br>";
					}
					str += "<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
				"</div>" +
			"</div>";
			
			$("#div_fail_insert_turno").append(str);
	
			$("#div_fail_insert_turno").show(500);
    });
}


function loadSelectApprovazione(){

	if ($("#approvazione_form").length) {
		
		$("#sezioneFormApprovazione").hide();
		
		$("#regioneApprovazione").empty();
		$("#provinciaApprovazione").empty();
		$("#comuneApprovazione").empty();
		$("#sezioneApprovazioneFailResponse").empty();
		$("#sezioneApprovazioneFailResponse").hide();
		
		$(".HideInputProvincia").hide();
		$(".HideInputComune").hide();
		$(".HideInputCentroVaccinale").hide();
		

	    $("#text-scelta-approvazione").text("Seleziona la regione");
	    
	    
		//carico le regioni
		loadRegioniGenerico("#regioneApprovazione");
		
		// al change sulla select regione fa il load delle corrispettive province nella select province
		$("#regioneApprovazione").change(function(){
			
			$("#provinciaApprovazione").empty();
			$("#comuneApprovazione").empty();
			$("#centroVaccinaleApprovazione").empty();
			$("#text-scelta-approvazione").empty();
			$("#sezioneApprovazioneFailResponse").empty();
			$("#sezioneApprovazioneFailResponse").hide();
			
			//nascondi
			$(".HideInputComune").hide();
			$(".HideInputCentroVaccinale").hide();
			
			//mostra la select provincia
			$(".HideInputProvincia").show(500);
		    $("#text-scelta-approvazione").text("Seleziona la provincia");
		    
		    //carica province in base alla regione passata
		    loadProvinceGenerico("#provinciaApprovazione", $("#regioneApprovazione option:selected").val());
		    
		});

		
		
		// al change sulla select province fa il load sulla select dei comuni corrispondenti alla provincia,
		// e inoltre fa il load dei centri vaccinali nella provincia
		$("#provinciaApprovazione").change(function(){
			$("#comuneApprovazione").empty();
			$("#centroVaccinaleApprovazione").empty();
			$("#text-scelta-approvazione").empty();
			$("#sezioneApprovazioneFailResponse").empty();
			$("#sezioneApprovazioneFailResponse").hide();

			//mostra
			$(".HideInputComune").show(500);
			$(".HideInputCentroVaccinale").show(500);
		    $("#text-scelta-approvazione").text("Seleziona il centro vaccinale o specifica il comune");
		    
		    // carico i centri vaccinali in base alla regione, alla provincia
		    loadListCentriVaccinaliGenerico("#centroVaccinaleApprovazione", $("#regioneApprovazione option:selected").val(), $("#provinciaApprovazione option:selected").val());
		    
		    // carico i comuni in base alla regione e alla provincia selezionata
			loadComuniGenerico("#comuneApprovazione ", $("#regioneApprovazione option:selected").val(), $("#provinciaApprovazione option:selected").val())
		})

		
		$("#comuneApprovazione").change(function(){
			$("#centroVaccinaleApprovazione").empty();
			$("#text-scelta-approvazione").empty();
			$("#sezioneApprovazioneFailResponse").empty();
			$("#sezioneApprovazioneFailResponse").hide();
			
			//mostra
			$(".HideInputCentroVaccinale").show(500);
		    $("#text-scelta-approvazione").text("Seleziona il centro vaccinale");
		    
		    loadListCentriVaccinaliGenerico("#centroVaccinaleApprovazione", $("#regioneApprovazione option:selected").val(), $("#provinciaApprovazione option:selected").val(), $("#comuneApprovazione option:selected").val());
		
		})
		
	}
}


function verificaApprovazione(){
	
	$("#sezioneApprovazioneFailResponse").empty();
	$("#sezioneApprovazioneFailResponse").hide();
	
	$("#sezioneApprovazioneSuccessResponse").empty();
	$("#sezioneApprovazioneSuccessResponse").hide();
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post( "/googlielmo93/info_dipendente_approvato", () =>{loadSpinner()})
	.done(
		function(data) {
			loadSpinner(true);
			
			if(data.result && data.data[0] && Object.keys(data.result).length 
			   && Object.keys(data.data[0]).length && data.result == "success"){
					
					$("#sezioneApprovazioneFailResponse").hide();
					
					let infoUser = data.data[0];
					
					if(infoUser.utente_abilitato == "1"){	//se abilitato nasconde il form e mostra le icone
						$("#sezioneFormApprovazione").hide();
						$("#sezioneIcon").show(500);
						return;
						
					}else if(infoUser.utente_abilitato == "0"){	//se non abilitato ma presente la richiesta mostra un messaggio di attesa
						
						let str =
							"<div id=\"success_insert_approvazione\" class=\"card border-success p-3 mb-5 mx-auto alert alert-success\" style=\"width: 100%; font-size:.92rem;\" >" +
								"<div class=\"row\">" +
									"<div class=\"col-sm-11\"></div>" +
								"</div>" +
								"<div class=\"card-body\">" +
									"<h5 class=\"card-title\">Richiesta di approvazione pendente! Si prega di attendere la conferma da parte di un operatore Asp.</h5>" +
								"</div>" +
							"</div>";

						$("#sezioneIcon").hide();
						$("#sezioneApprovazioneFailResponse").hide();
						$("#sezioneFormApprovazione").hide();

						$("#sezioneApprovazioneSuccessResponse").append(str);
						$("#sezioneApprovazioneSuccessResponse").show(500);

						return;
					}
				
			}else{

				let str =
					"<div id=\"fail_insert_verifica_approvazione\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
						"<div class=\"row\">" +
							"<div class=\"col-sm-11\"></div>" +
							"<button type=\"button\" id=\"closeAlertApprovazione\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
						"</div>" +
						"<div class=\"card-body\">" +
							"<h5 class=\"card-title\">Verifica di approvazione non andata a buon fine!</h5>" +
							"<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
						"</div>" +
					"</div>";
					
					//close alert message
					if ($("#fail_insert_verifica_approvazione").length) { 
				 		$("button #closeAlertApprovazione").click( ()=> {
							$("#fail_insert_verifica_approvazione").fadeOut(500);
						});
					}
					$("#sezioneApprovazioneFailResponse").append(str);

					$("#sezioneApprovazioneFailResponse").show(500);
					
			}
			
				loadSelectApprovazione();
				$("#sezioneIcon").hide();
				
				if($("#sezioneFormApprovazione").is(":hidden"))
					$("#sezioneFormApprovazione").show(500);
			
	})
	.fail(function(){
		let str =
			"<div id=\"fail_insert_verifica_approvazione\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
				"<div class=\"row\">" +
					"<div class=\"col-sm-11\"></div>" +
					"<button type=\"button\" id=\"closeAlertApprovazione\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
				"</div>" +
				"<div class=\"card-body\">" +
					"<h5 class=\"card-title\">Verifica di approvazione non andata a buon fine!</h5>" +
					"<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
				"</div>" +
			"</div>";
			
			//close alert message
			if ($("#fail_insert_verifica_approvazione").length) { 
		 		$("button #closeAlertApprovazione").click( ()=> {
					$("#fail_insert_verifica_approvazione").fadeOut(500);
				});
			}
			$("#sezioneApprovazioneFailResponse").append(str);

			$("#sezioneApprovazioneFailResponse").show(500);
		
			loadSelectApprovazione();
			$("#sezioneIcon").hide();
			
			if($("#sezioneFormApprovazione").is(":hidden"))
				$("#sezioneFormApprovazione").show(500);
		
	});
	
}


function richiestaApprovazione(){
	
	$("#sezioneApprovazioneFailResponse").empty();
	$("#sezioneApprovazioneFailResponse").hide();
	$("#div_fail_insert_approvazione_asp").empty();
	$("#div_fail_insert_approvazione_asp").hide();
	
	$("#sezioneApprovazioneSuccessResponse").empty();
	$("#sezioneApprovazioneSuccessResponse").hide();
	
	let idCentroVaccinaleApprovazione = $("#centroVaccinaleApprovazione option:selected").val();
    
	let dataForm = {
		operation : "richiesta_approvazione",
		id_centro_vaccinale : idCentroVaccinaleApprovazione
    };
    
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post( "/googlielmo93/info_dipendente_approvato", dataForm, () =>{loadSpinner()})
	.done(
		function(data) {
			loadSpinner(true);
			
			if(data.result && data.data[0] && Object.keys(data.result).length 
			   && Object.keys(data.data[0]).length && data.result == "success"){
					
					$("#div_fail_insert_approvazione_asp").hide();
					
					let infoUser = data.data[0];
					
					if(infoUser.utente_abilitato == "1"){	//se abilitato nasconde il form e mostra le icone
						$("#sezioneFormApprovazione").hide();
						$("#sezioneIcon").show(500);

						return;
						
					}else if(infoUser.utente_abilitato == "0"){	//se non abilitato ma presente la richiesta mostra un messaggio di attesa
						
						let str =
							"<div id=\"success_insert_approvazione\" class=\"card border-success p-3 mb-5 mx-auto alert alert-success\" style=\"width: 100%; font-size:.92rem;\" >" +
								"<div class=\"row\">" +
									"<div class=\"col-sm-11\"></div>" +
								"</div>" +
								"<div class=\"card-body\">" +
									"<h5 class=\"card-title\">Richiesta di approvazione avvenuta con successo! Si prega di attendere la conferma da parte di un operatore Asp.</h5>" +
								"</div>" +
							"</div>";
						

						$("#sezioneIcon").hide();
						$("#div_fail_insert_approvazione_asp").hide();
						$("#sezioneFormApprovazione").hide();

							
						$("#sezioneApprovazioneSuccessResponse").append(str);
						
						$("#sezioneApprovazioneSuccessResponse").show(500);

						return;
					}
				
				}else if(data.result=="fail"){
						
						let str =
						"<div id=\"fail_insert_richiesta_approvazione\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
							"<div class=\"row\">" +
								"<div class=\"col-sm-11\"></div>" +
								"<button type=\"button\" id=\"closeAlertApprovazione\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
							"</div>" +
							"<div class=\"card-body\">" +
								"<h5 class=\"card-title\">Richiesta di approvazione non andata a buon fine!</h5>" +
								"<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
							"</div>" +
						"</div>";
						
						//close alert message
						if ($("#fail_insert_richiesta_approvazione").length) { 
					 		$("button #closeAlertApprovazione").click( ()=> {
								$("#fail_insert_richiesta_approvazione").fadeOut(500);
							});
						}
						$("#sezioneApprovazioneFailResponse").append(str);
			
						$("#sezioneApprovazioneFailResponse").show(500);
						
						return;
				}
			
			//loadSelectApprovazione();
			$("#sezioneIcon").hide();
			
			if($("#sezioneFormApprovazione").is(":hidden"))
				$("#sezioneFormApprovazione").show(500);
			
			
	})
	.fail(function(){
		let str =
		"<div id=\"fail_insert_richiesta_approvazione\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
			"<div class=\"row\">" +
				"<div class=\"col-sm-11\"></div>" +
				"<button type=\"button\" id=\"closeAlertApprovazione\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
			"</div>" +
			"<div class=\"card-body\">" +
				"<h5 class=\"card-title\">Richiesta di approvazione non andata a buon fine!</h5>" +
				"<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
			"</div>" +
		"</div>";
		
		//close alert message
		if ($("#fail_insert_richiesta_approvazione").length) { 
	 		$("button #closeAlertApprovazione").click( ()=> {
				$("#fail_insert_richiesta_approvazione").fadeOut(500);
			});
		}
		$("#sezioneApprovazioneFailResponse").append(str);

		$("#sezioneApprovazioneFailResponse").show(500);
	
		loadSelectApprovazione();
		$("#sezioneIcon").hide();
		
		if($("#sezioneFormApprovazione").is(":hidden"))
			$("#sezioneFormApprovazione").show(500);
		
	});
	
}


/* FINE FUNZIONI ACCETTAZIONE */


/* FUNZIONI OP ASP */

function approvaDipendente(id_dipendente){

	let dataForm = {
		operation : "approva_richiesta",
		id_dipendente : id_dipendente
	};

	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post(
		"/googlielmo93/approva_dipendente", dataForm, () =>{loadSpinner()}
	)
	.done(
		function(data) {
			loadSpinner(true);
			
			if(data.result && Object.keys(data.result).length && data.result == "success"){
					
				
				if($("#dip_"+id_dipendente).length){
					$("#dip_"+id_dipendente+" .card-body").css("opacity", "0.2");
					let str = "<div class=\"col-sm-12 mx-auto my-auto\">" +
								"<i class=\"far fa-check-circle\" style=\"color:#66cdaa; font-size: 5rem;\"></i>" +
							  "</div>";
					$("#dip_"+id_dipendente).append(str);
					setTimeout(function () {
						$("#dip_"+id_dipendente).remove(); 
						if($("#richiestePendenti").children().length == 0){
							$("#div_fail_insert_approvazione_asp").empty();
							
							let str =
							"<div id=\"fail_insert_approvazione\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
								"<div class=\"card-body\">" +
									"<h5 class=\"card-title\">Nessun dipendente da approvare per questo centro vaccinale</h5>" +
									"<h6>Seleziona un altro centro vaccinale per cercare richieste pendenti</h6>" +
								"</div>" +
							"</div>";
							
							$("#div_fail_insert_approvazione_asp").append(str);
					
							$("#div_fail_insert_approvazione_asp").show(500);
						}
					}, 750);
				}
				
			}else{
				
				$("#div_fail_insert_approvazione_asp").empty();
				
				let str =
				"<div id=\"fail_insert_approvazione\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
					"<div class=\"row\">" +
						"<div class=\"col-sm-11\"></div>" +
						"<button type=\"button\" id=\"closeAlertApprovazione\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
					"</div>" +
					"<div class=\"card-body\">" +
						"<h5 class=\"card-title\">Approvazione non andata a buon fine!</h5>" +
						"<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
					"</div>" +
				"</div>";
				
				//close alert message
				if ($("#fail_insert_approvazione").length) { 
			 		$("button #closeAlertApprovazione").click( ()=> {
						$("#fail_insert_approvazione").fadeOut(500);
					});
				}
				
				$("#div_fail_insert_approvazione_asp").append(str);
		
				$("#div_fail_insert_approvazione_asp").show(500);

			}
			
	})
	.fail(function(){
		$("#div_fail_insert_approvazione_asp").empty();
		
		let str =
		"<div id=\"fail_insert_approvazione\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
			"<div class=\"row\">" +
				"<div class=\"col-sm-11\"></div>" +
				"<button type=\"button\" id=\"closeAlertApprovazione\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
			"</div>" +
			"<div class=\"card-body\">" +
				"<h5 class=\"card-title\">Approvazione non andata a buon fine!</h5>" +
				"<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
			"</div>" +
		"</div>";
		
		//close alert message
		if ($("#fail_insert_approvazione").length) { 
	 		$("button #closeAlertApprovazione").click( ()=> {
				$("#fail_insert_approvazione").fadeOut(500);
			});
		}
		
		$("#div_fail_insert_approvazione_asp").append(str);

		$("#div_fail_insert_approvazione_asp").show(500);
	});
	
}


//carica le approvazioni pendenti per centro vaccinale
function loadApprovazioniPendenti(id_centro_vaccinale){
	$("#sezioneApprovazioneFailResponse").empty();
	$("#sezioneApprovazioneFailResponse").hide();
	$("#div_fail_insert_approvazione_asp").empty();
	$("#div_fail_insert_approvazione_asp").hide();
	
	let dataForm = {
		operation : "listApprovazioniPendenti",
		id_centro_vaccinale : id_centro_vaccinale
	};

	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post(
		"/googlielmo93/list_approvazioni_pendenti", 
		dataForm, () =>{loadSpinner()}
	)
	.done(
		function(data) {
			loadSpinner(true);

			$(".HideInputRichiestePendenti").show(500);
			
			let approvazioniNum = 0;
			
			if(data.data)
				approvazioniNum = data.data.length;
			
			
			if(data.result == "success" && approvazioniNum !== 0){
					
				let str="";
				
				$("#richiestePendenti").empty();
				
				$.each(data.data, function (index, value) {
					
					str="";
					
				    let info_dipendente = value.info_dipendente_approvato.info_dipendente;
				    
				    if(info_dipendente!=undefined && info_dipendente!=""){
				    	
				    	str += "<div id=\"dip_"+info_dipendente.id_dipendente+"\" class=\"card border-info m-2\" style=\"width: 14rem; font-size:.92rem; cursor: pointer;";
		    			if(index >= 6)
		    				str += "display : none!important;";
				    			str += "\" >" +
				    				"<div class=\"card-body\">" +
				    				"<h5 class=\"card-title \">Id Richiesta<br>" +
				    					value.info_dipendente_approvato.info_relazione_dipendenza.id_dipendente_approvato +
				    				"</h5>" +
				    					"<div class=\"col-md-12 my-5 text-center card_mod\" id=\"buttonApprovazioneDipendente\" onclick = ' approvaDipendente("+info_dipendente.id_dipendente+"); '><h5>Approva utenza&nbsp;<i class=\"fas fa-chevron-circle-right\" style=\"color: #17a2b8!important;\"></i></h5></div>" +

					    				
				    					"<h6 class=\"text-center\">" +
				    						"Nome: " + info_dipendente.nome + "<br>" +
				    						"Cognome: "+ info_dipendente.cognome + "</br>" +
				    						"Ruolo: " + info_dipendente.ruolo +
				    					"</h6>" +
				    					
				    					"<div class=\"col-md-12 my-3 text-center card_mod\" data-toggle=\"modal\" data-target=\"#infoDipendenteModale_"+info_dipendente.id_dipendente+"\" id=\"buttonVediInfo\"><h6>Vedi pi&ugrave; info <i class=\"fas fa-plus-square\" style=\"color: #17a2b8!important;\"></i></h6></div>" +
				    					
				    				"<div class=\"modal fade\" id=\"infoDipendenteModale_"+info_dipendente.id_dipendente+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"infoDipendenteExtraTable\" aria-hidden=\"true\">" +
				    					"<div class=\"modal-dialog modal-dialog-centered\" role=\"document\">" +
				    						"<div class=\"modal-content\">" +
				    							"<div class=\"modal-header\">" +
				    								"<h5 class=\"modal-title\" id=\"infoDipendenteModaleTitle\">Informazioni dipendente</h5>" +
				    								"<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">" +
				    									"<span aria-hidden=\"true\">&times;</span>" +
				    								"</button>" +
				    							"</div>" +
				    							"<div class=\"modal-body\">" +
						    			      		"<div id=\"info_dipendente_da_approvare_div\">" +
								    					" <ul class=\"list-group\" style=\"text-align:	left!important;\">" +
									    					"<li class=\"list-group-item\">Nome: " + info_dipendente.nome + "</li>" +
									    					"<li class=\"list-group-item\">Cognome: " + info_dipendente.cognome + "</li>" +
									    					"<li class=\"list-group-item\">Email: " + info_dipendente.email + "</li>" +
									    					"<li class=\"list-group-item\">Sesso: " + info_dipendente.sesso + "</li>" +
									    					"<li class=\"list-group-item\">Codice Fiscale : " + info_dipendente.codice_fiscale + "</li>" +
									    					"<li class=\"list-group-item\">Data di nascita: " + info_dipendente.data_nascita + "</li>" +
									    					"<li class=\"list-group-item\">Regione di residenza: " + info_dipendente.regione + "</li>" +
									    					"<li class=\"list-group-item\">Provincia di residenza: " + info_dipendente.provincia + "</li>" +
									    					"<li class=\"list-group-item\">Comune di residenza: " + info_dipendente.citta + "</li>" +
										    			" </ul> " +
									    			"</div>" +
									    		"</div>" +
									    		"<div class=\"modal-footer\">" +
										    		"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Chiudi Info</button>" +
										    	"</div>" +
										    "</div>" +
										  "</div>" +
										"</div>" +
									"</div>" +
				    			"</div>";
				    			

								$("#richiestePendenti").append(str);
									
				    }
					 
				});

				str="";
				
				if(approvazioniNum > 6){
					str += "<div class=\"col-md-10 my-5 text-center\" style=\"cursor:pointer\" id=\"buttonVediAltri\"><h5>Vedi Altri</h5></div>";
				}
				
				$("#richiestePendenti").append(str);
				$("#richiestePendenti").show(500);
				
				
				if(approvazioniNum > 6){
					
					$("#buttonVediAltri").click(
						function(){
							let count = 0;
							$("#richiestePendenti").find(".card").each(
								(i, el) => {
									if($(el).is(":hidden")){
										$("#buttonVediAltri").hide();
										
										if(count >= 6){
											$("#buttonVediAltri").show();
											return;
										}
									
										count++;
										$(el).show();
									}
								}
							);
							
						}
					);
				}
				
			}else{
				
					$("#div_fail_insert_approvazione_asp").empty();
								
					let str =
					"<div id=\"fail_insert_approvazione\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
						"<div class=\"card-body\">" +
							"<h5 class=\"card-title\">Nessun dipendente da approvare per questo centro vaccinale</h5>" +
							"<h6>Seleziona un altro centro vaccinale per cercare richieste pendenti</h6>" +
						"</div>" +
					"</div>";
					
					$("#div_fail_insert_approvazione_asp").append(str);
			
					$("#div_fail_insert_approvazione_asp").show(500);
			}
			
	})
	.fail(function(){
		$("#div_fail_insert_approvazione_asp").empty();
				
			let str =
			"<div id=\"fail_insert_approvazione\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
				"<div class=\"card-body\">" +
					"<h5 class=\"card-title\">Nessuna approvazione da sottomettere</h5>" +
				"</div>" +
			"</div>";
			
			$("#div_fail_insert_approvazione_asp").append(str);
	
			$("#div_fail_insert_approvazione_asp").show(500);
	});
}


function loadSelectApprova(download=false){

	if ($("#asp_approvazione_form").length) {
		
		$("#regioneAsp").empty();
		$("#provinciaAsp").empty();
		$("#comuneAsp").empty();
		$("#div_fail_insert_asp").empty();
		$("#div_fail_insert_asp").hide();
		
		$(".HideInputProvincia").hide();
		$(".HideInputComune").hide();
		$(".HideInputCentroVaccinale").hide();
		$(".HideInputRichiestePendenti").hide();
		

	    $("#text-approvazione_dipendente").text("Seleziona la regione");
	    
	    
		//carico le regioni
		loadRegioniGenerico("#regioneAsp");
		
		// al change sulla select regione fa il load delle corrispettive province nella select province
		$("#regioneAsp").change(function(){
			
			$("#provinciaAsp").empty();
			$("#comuneAsp").empty();
			$("#centroVaccinaleAsp").empty();
			$("#text-approvazione_dipendente").empty();
			$("#div_fail_insert_asp").empty();
			$("#div_fail_insert_asp").hide();
			
			//nascondi
			$(".HideInputComune").hide();
			$(".HideInputCentroVaccinale").hide();
			$(".HideInputRichiestePendenti").hide();
			
			//mostra la select provincia
			$(".HideInputProvincia").show(500);
		    $("#text-approvazione_dipendente").text("Seleziona la provincia");
		    
		    //carica province in base alla regione passata
		    loadProvinceGenerico("#provinciaAsp", $("#regioneAsp option:selected").val());
		    
		});

		
		
		// al change sulla select province fa il load sulla select dei comuni corrispondenti alla provincia,
		// e inoltre fa il load dei centri vaccinali nella provincia
		$("#provinciaAsp").change(function(){
			$("#comuneAsp").empty();
			$("#centroVaccinaleAsp").empty();
			$("#text-approvazione_dipendente").empty();
			$("#div_fail_insert_asp").empty();
			$("#div_fail_insert_asp").hide();

			//mostra
			$(".HideInputComune").show(500);
			$(".HideInputCentroVaccinale").show(500);
			$(".HideInputRichiestePendenti").hide();
		    $("#text-approvazione_dipendente").text("Seleziona il centro vaccinale o specifica il comune");
		    
		    // carico i centri vaccinali in base alla regione, alla provincia
		    loadListCentriVaccinaliGenerico("#centroVaccinaleAsp", $("#regioneAsp option:selected").val(), $("#provinciaAsp option:selected").val());
		    
		    // carico i comuni in base alla regione e alla provincia selezionata
			loadComuniGenerico("#comuneAsp ", $("#regioneAsp option:selected").val(), $("#provinciaAsp option:selected").val())
		})

		
		$("#comuneAsp").change(function(){
			$("#centroVaccinaleAsp").empty();
			$("#text-approvazione_dipendente").empty();
			$("#div_fail_insert_asp").empty();
			$("#div_fail_insert_asp").hide();
			
			//mostra
			$(".HideInputCentroVaccinale").show(500);
			$(".HideInputRichiestePendenti").hide();
		    $("#text-approvazione_dipendente").text("Seleziona il centro vaccinale");
		    
		    loadListCentriVaccinaliGenerico("#centroVaccinaleAsp", $("#regioneAsp option:selected").val(), $("#provinciaAsp option:selected").val(), $("#comuneAsp option:selected").val());
		
		})
		
		$("#centroVaccinaleAsp").change(function(){
			$("#richiestePendenti").empty();
			$("#text-approvazione_dipendente").empty();
			$("#div_fail_insert_asp").empty();
			$("#div_fail_insert_asp").hide();
			
			//mostra
			$(".HideInputCentroVaccinale").show(500);
			$(".HideInputRichiestePendenti").show(500);
		    $("#text-approvazione_dipendente").text("Seleziona il dipendente da confermare");
		    
		    if(!download)
		    	loadApprovazioniPendenti($("#centroVaccinaleAsp option:selected").val());
		    else{
		    	let comune = $("#comuneAsp option:selected").val()
		    	downloadDatiXML($("#regioneAsp option:selected").val(), $("#provinciaAsp option:selected").val(), $("#comuneAsp option:selected").val())
		    }
		
		})
		
	}
}



function loadSelectCentroVaccinaleAdd(){

	if ($("#centro_vaccinale_add_form").length) {
		
		$("#regioneCentroVaccinaleAdd").empty();
		$("#provinciaCentroVaccinaleAdd").empty();
		$("#comuneCentroVaccinaleAdd").empty();
		$("#div_msg_insert_centro_vaccinale").empty();
		$("#div_msg_insert_centro_vaccinale").hide();
		$("#sezioneAspSuccessResponse").empty();
		$("#sezioneAspSuccessResponse").hide();
		$("#sezioneApprovazioneFailResponse").empty();
		$("#sezioneApprovazioneFailResponse").hide();
		
		$(".HideInputProvincia").hide();
		$(".HideInputComune").hide();
		$(".HideInputDenominazione").hide();
		$(".HideInputIndirizzo").hide();
		$("#submitCentroVaccinaleAdd").hide();
		$("#denominazioneCentroVaccinaleAdd").val("");
		$("#indirizzoCentroVaccinaleAdd").val("");
		

	    $("#text-centro_vaccinale_add").text("Seleziona la regione");
	    
	    
		//carico le regioni
		loadRegioniGenerico("#regioneCentroVaccinaleAdd");
		
		// al change sulla select regione fa il load delle corrispettive province nella select province
		$("#regioneCentroVaccinaleAdd").change(function(){
			$("#div_msg_insert_centro_vaccinale").empty();
			$("#div_msg_insert_centro_vaccinale").hide();
			$("#sezioneAspSuccessResponse").empty();
			$("#sezioneAspSuccessResponse").hide();
			$("#sezioneApprovazioneFailResponse").empty();
			$("#sezioneApprovazioneFailResponse").hide();
			
			$("#provinciaCentroVaccinaleAdd").empty();
			$("#comuneCentroVaccinaleAdd").empty();
			$("#text-centro_vaccinale_add").empty();
			$("#div_msg_insert_centro_vaccinale").empty();
			$("#div_msg_insert_centro_vaccinale").hide();
			
    		$(".HideInputProvincia").hide();
    		$(".HideInputComune").hide();
    		$(".HideInputDenominazione").hide();
    		$(".HideInputIndirizzo").hide();
    		$("#submitCentroVaccinaleAdd").hide();
    		$("#denominazioneCentroVaccinaleAdd").val("");
    		$("#indirizzoCentroVaccinaleAdd").val("");
			
			//mostra la select provincia
			$(".HideInputProvincia").show(500);
		    $("#text-centro_vaccinale_add").text("Seleziona la provincia");
		    
		    //carica province in base alla regione passata
		    loadProvinceGenerico("#provinciaCentroVaccinaleAdd", $("#regioneCentroVaccinaleAdd option:selected").val());
		    
		});

		
		
		// al change sulla select province fa il load sulla select dei comuni corrispondenti alla provincia,
		// e inoltre fa il load dei centri vaccinali nella provincia
		$("#provinciaCentroVaccinaleAdd").change(function(){
			$("#div_msg_insert_centro_vaccinale").empty();
			$("#div_msg_insert_centro_vaccinale").hide();
			$("#sezioneAspSuccessResponse").empty();
			$("#sezioneAspSuccessResponse").hide();
			$("#sezioneApprovazioneFailResponse").empty();
			$("#sezioneApprovazioneFailResponse").hide();
			
			$("#comuneCentroVaccinaleAdd").empty();
			$("#text-centro_vaccinale_add").empty();
			$("#div_msg_insert_centro_vaccinale").empty();
			$("#div_msg_insert_centro_vaccinale").hide();
			
    		$(".HideInputComune").hide();
    		$(".HideInputDenominazione").hide();
    		$(".HideInputIndirizzo").hide();
    		$("#submitCentroVaccinaleAdd").hide();
    		$("#denominazioneCentroVaccinaleAdd").val("");
    		$("#indirizzoCentroVaccinaleAdd").val("");
    		
			//mostra
			$(".HideInputComune").show(500);
		    $("#text-centro_vaccinale_add").text("Seleziona il comune");
		    
		    // carico i comuni in base alla regione e alla provincia selezionata
			loadComuniGenerico("#comuneCentroVaccinaleAdd ", $("#regioneCentroVaccinaleAdd option:selected").val(), $("#provinciaCentroVaccinaleAdd option:selected").val())
		})

		
		$("#comuneCentroVaccinaleAdd").change(function(){
			$("#div_msg_insert_centro_vaccinale").empty();
			$("#div_msg_insert_centro_vaccinale").hide();
			$("#sezioneAspSuccessResponse").empty();
			$("#sezioneAspSuccessResponse").hide();
			$("#sezioneApprovazioneFailResponse").empty();
			$("#sezioneApprovazioneFailResponse").hide();
			
			$("#text-centro_vaccinale_add").empty();
			$("#div_msg_insert_centro_vaccinale").empty();
			$("#div_msg_insert_centro_vaccinale").hide();
			
    		$(".HideInputDenominazione").hide();
    		$(".HideInputIndirizzo").hide();
    		$("#submitCentroVaccinaleAdd").hide();
    		$("#denominazioneCentroVaccinaleAdd").val("");
    		$("#indirizzoCentroVaccinaleAdd").val("");
			
		    $("#text-centro_vaccinale_add").text("Inserisci la denominazione");
		    $(".HideInputDenominazione").show(500);
			$("#denominazioneCentroVaccinaleAdd").show();
		});
		
		$("#denominazioneCentroVaccinaleAdd").change(function(){
			$("#div_msg_insert_centro_vaccinale").empty();
			$("#div_msg_insert_centro_vaccinale").hide();
			$("#sezioneAspSuccessResponse").empty();
			$("#sezioneAspSuccessResponse").hide();
			$("#sezioneApprovazioneFailResponse").empty();
			$("#sezioneApprovazioneFailResponse").hide();
			
			$("#text-centro_vaccinale_add").empty();
			$("#div_msg_insert_centro_vaccinale").empty();
			$("#div_msg_insert_centro_vaccinale").hide();
			
    		$(".HideInputIndirizzo").hide();
    		$("#submitCentroVaccinaleAdd").hide();
    		$("#indirizzoCentroVaccinaleAdd").val("");
			
    		$("#text-centro_vaccinale_add").text("Inserisci l'indirizzo");
			$(".HideInputIndirizzo").show(500);
		});
		
		$("#indirizzoCentroVaccinaleAdd").change(function(){
			$("#div_msg_insert_centro_vaccinale").empty();
			$("#div_msg_insert_centro_vaccinale").hide();
			$("#sezioneAspSuccessResponse").empty();
			$("#sezioneAspSuccessResponse").hide();
			$("#sezioneApprovazioneFailResponse").empty();
			$("#sezioneApprovazioneFailResponse").hide();
			
		    $("#submitCentroVaccinaleAdd").show(500);
		});
		
		
	}
}



function loadSelectDateApertura(){

	if ($("#date_apertura_form").length) {
		
		$("#regioneDateApertura").empty();
		$("#provinciaDateApertura").empty();
		$("#comuneDateApertura").empty();
		$("#div_fail_insert_data_apertura").empty();
		$("#div_fail_insert_data_apertura").hide();
		
		$(".HideInputProvincia").hide();
		$(".HideInputComune").hide();
		$(".HideInputCentroVaccinale").hide();
		$(".HideInputInfoApertura").hide();
		$("#submitDataApertura").hide();
		

	    $("#text-date_apertura").text("Seleziona la regione");
	    
	    
		//carico le regioni
		loadRegioniGenerico("#regioneDateApertura");
		
		// al change sulla select regione fa il load delle corrispettive province nella select province
		$("#regioneDateApertura").change(function(){
			
			$("#provinciaDateApertura").empty();
			$("#comuneDateApertura").empty();
			$("#centroVaccinaleDateApertura").empty();
			$("#text-date_apertura").empty();
			$("#div_fail_insert_data_apertura").empty();
			$("#div_fail_insert_data_apertura").hide();
			$("#dataAperturaInsert").val("");
			$("#postiDisponibiliInput").val("");
			$(".HideInputInfoApertura").hide();
			$("#submitDataApertura").hide();
			
			//nascondi
			$(".HideInputComune").hide();
			$(".HideInputCentroVaccinale").hide();
			$(".HideInputInfoApertura").hide();
			
			//mostra la select provincia
			$(".HideInputProvincia").show(500);
		    $("#text-date_apertura").text("Seleziona la provincia");
		    
		    //carica province in base alla regione passata
		    loadProvinceGenerico("#provinciaDateApertura", $("#regioneDateApertura option:selected").val());
		    
		});

		
		
		// al change sulla select province fa il load sulla select dei comuni corrispondenti alla provincia,
		// e inoltre fa il load dei centri vaccinali nella provincia
		$("#provinciaDateApertura").change(function(){
			$("#comuneDateApertura").empty();
			$("#centroVaccinaleDateApertura").empty();
			$("#text-date_apertura").empty();
			$("#div_fail_insert_data_apertura").empty();
			$("#div_fail_insert_data_apertura").hide();
			$("#dataAperturaInsert").val("");
			$("#postiDisponibiliInput").val("");
			$(".HideInputInfoApertura").hide();
			$("#submitDataApertura").hide();
			//mostra
			$(".HideInputComune").show(500);
			$(".HideInputCentroVaccinale").show(500);
			$(".HideInputInfoApertura").hide();
		    $("#text-date_apertura").text("Seleziona il centro vaccinale o specifica il comune");
		    
		    // carico i centri vaccinali in base alla regione, alla provincia
		    loadListCentriVaccinaliGenerico("#centroVaccinaleDateApertura", $("#regioneDateApertura option:selected").val(), $("#provinciaDateApertura option:selected").val());
		    
		    // carico i comuni in base alla regione e alla provincia selezionata
			loadComuniGenerico("#comuneDateApertura ", $("#regioneDateApertura option:selected").val(), $("#provinciaDateApertura option:selected").val())
		})

		
		$("#comuneDateApertura").change(function(){
			$("#centroVaccinaleDateApertura").empty();
			$("#text-date_apertura").empty();
			$("#div_fail_insert_data_apertura").empty();
			$("#div_fail_insert_data_apertura").hide();
			$("#dataAperturaInsert").val("");
			$("#postiDisponibiliInput").val("");
			$(".HideInputInfoApertura").hide();
			$("#submitDataApertura").hide();
			
			//mostra
			$(".HideInputCentroVaccinale").show(500);
			$(".HideInputInfoApertura").hide();
		    $("#text-date_apertura").text("Seleziona il centro vaccinale");
		    
		    loadListCentriVaccinaliGenerico("#centroVaccinaleDateApertura", $("#regioneDateApertura option:selected").val(), $("#provinciaDateApertura option:selected").val(), $("#comuneDateApertura option:selected").val());
		
		})
		
		$("#centroVaccinaleDateApertura").change(function(){
			$("#text-date_apertura").empty();
			$("#div_fail_insert_data_apertura").empty();
			$("#div_fail_insert_data_apertura").hide();
			$("#dataAperturaInsert").val("");
			$("#postiDisponibiliInput").val("");
			$(".HideInputInfoApertura").hide();
			$("#div_msg_insert_data_apertura").hide();
			$("#div_msg_insert_data_apertura").empty();
			$("#submitDataApertura").hide();
			
			//mostra
			$(".HideInputCentroVaccinale").show(500);
			$(".HideInputInfoApertura").show(500);
		    $("#text-date_apertura").text("Inserisci la data e il numero di posti disponibili");
		    
		    $("#submitDataApertura").show(500);
		    
		})
		
		$("#postiDisponibiliInput").change(function(){
		    $("#submitDataApertura").show(500);
		})
		
	}
}



function inserisciCentroVaccinale(regione, provincia, comune, denominazione, indirizzo){
	
	$("#div_msg_insert_centro_vaccinale").hide();
	$("#div_msg_insert_centro_vaccinale").empty();

	let dataForm = {
		regione: regione,
		provincia: provincia,
		comune: comune,
		denominazione: denominazione,
		indirizzo: indirizzo
	};
	
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post(
		"/googlielmo93/centro_vaccinale_add", dataForm, () =>{loadSpinner()}
	)
	.done(
		function(data) {
			loadSpinner(true);
			
			if(data.result && Object.keys(data.result).length && data.result == "success"){
				
				$("#sezioneAddCentroVaccinaleSuccessResponse").empty();
	
				let str =
					"<div id=\"success_insert_centro_vaccinale_add\" class=\"card border-success col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-success\" style=\"width: 100%; font-size:.92rem;\" >" +
						"<div class=\"row\">" +
							"<div class=\"col-sm-11\"></div>" +
							"<button type=\"button\" id=\"closeAlertCentroVaccinaleAdd\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
						"</div>" +
						"<div class=\"card-body\">" +
							"<h5 class=\"card-title\">Centro Vaccinale inserito con successo!</h5>" +
						"</div>" +
					"</div>";
					
					//close alert message
					if ($("#success_insert_centro_vaccinale_add").length) { 
				 		$("button #closeAlertCentroVaccinaleAdd").click( ()=> {
							$("#success_insert_centro_vaccinale_add").fadeOut(500);
						});
					}
					
					$("#sezioneAddCentroVaccinaleSuccessResponse").append(str);
			

					$("#sezioneFormCentroVaccinaleAdd").hide(500);
					$("#regioneCentroVaccinaleAdd").empty();
					$("#provinciaCentroVaccinaleAdd").empty();
					$("#comuneCentroVaccinaleAdd").empty();
					$("#div_msg_insert_centro_vaccinale").empty();
					$("#div_msg_insert_centro_vaccinale").hide();
					
					$(".HideInputProvincia").hide();
					$(".HideInputComune").hide();
					$(".HideInputDenominazione").hide();
					$(".HideInputIndirizzo").hide();
					$("#denominazioneCentroVaccinaleAdd").val("");
					$("#indirizzoCentroVaccinaleAdd").val("");
					$("#submitCentroVaccinaleAdd").hide();
					
					$("#sezioneAddCentroVaccinaleSuccessResponse").show(500);
					$("#centro_vaccinale_add_form").removeClass('was-validated');
				
			}else{
				
				$("#div_msg_insert_centro_vaccinale").empty();
				
				let str =
				"<div id=\"fail_insert_centro_vaccinale_add\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
					"<div class=\"row\">" +
						"<div class=\"col-sm-11\"></div>" +
						"<button type=\"button\" id=\"closeAlertCentroVaccinaleAdd\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
					"</div>" +
					"<div class=\"card-body\">" +
						"<h5 class=\"card-title\">";
						if(data.message.length)
							str += data.message;
						else
							str += "Inserimento del centro vaccinale non andato a buon fine!</h5>" ;
						str += "<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
					"</div>" +
				"</div>";
				
				//close alert message
				if ($("#fail_insert_centro_vaccinale_add").length) { 
			 		$("button #closeAlertCentroVaccinaleAdd").click( ()=> {
						$("#fail_insert_centro_vaccinale_add").fadeOut(500);
					});
				}
				
				$("#div_msg_insert_centro_vaccinale").append(str);
		
				$("#div_msg_insert_centro_vaccinale").show(500);
	
			}
			
	})
	.fail(function(){

		$("#div_msg_insert_centro_vaccinale").empty();
		
		let str =
		"<div id=\"fail_insert_centro_vaccinale_add\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
			"<div class=\"row\">" +
				"<div class=\"col-sm-11\"></div>" +
				"<button type=\"button\" id=\"closeAlertCentroVaccinaleAdd\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
			"</div>" +
			"<div class=\"card-body\">" +
				"<h5 class=\"card-title\">";
				if(data.message.length)
					str += data.message;
				else
					str += "Inserimento del centro vaccinale non andato a buon fine!</h5>" ;
				str += "<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
			"</div>" +
		"</div>";
		
		//close alert message
		if ($("#fail_insert_centro_vaccinale_add").length) { 
	 		$("button #closeAlertCentroVaccinaleAdd").click( ()=> {
				$("#fail_insert_centro_vaccinale_add").fadeOut(500);
			});
		}
		
		$("#div_msg_insert_centro_vaccinale").append(str);

		$("#div_msg_insert_centro_vaccinale").show(500);
		
	});
	
}



function inserisciDataApertura(id_centro_vaccinale, data_apertura, posti_disponibili){
	
	$("#div_msg_insert_data_apertura").hide();
	$("#div_msg_insert_data_apertura").empty();

	let dataForm = {
		id_centro_vaccinale : id_centro_vaccinale,
		data_apertura : data_apertura,
		posti_disponibili : posti_disponibili
	};
	
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post(
		"/googlielmo93/data_apertura", dataForm, () =>{loadSpinner()}
	)
	.done(
		function(data) {
			loadSpinner(true);
			
			if(data.result && Object.keys(data.result).length && data.result == "success"){

				$("#div_msg_insert_data_apertura").empty();
				
				let str =
					"<div id=\"success_insert_data_apertura\" class=\"card border-success col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-success\" style=\"width: 100%; font-size:.92rem;\" >" +
						"<div class=\"row\">" +
							"<div class=\"col-sm-11\"></div>" +
							"<button type=\"button\" id=\"closeAlertDataApertura\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
						"</div>" +
						"<div class=\"card-body\">" +
							"<h5 class=\"card-title\">Data inserita con successo!</h5>" +
							"<h6>Si pu&ograve; procedere con un'altro inserimento per lo stesso centro vaccinale oppure puoi selezionarne un&grave;altro</h6>" +
						"</div>" +
					"</div>";
					
					//close alert message
					if ($("#success_insert_data_apertura").length) { 
				 		$("button #closeAlertDataApertura").click( ()=> {
							$("#success_insert_data_apertura").fadeOut(500);
						});
					}
					
					$("#div_msg_insert_data_apertura").append(str);
			
					$("#div_msg_insert_data_apertura").show(500);
				
			}else{
				
				$("#div_msg_insert_data_apertura").empty();
				
				let str =
				"<div id=\"fail_insert_data_apertura\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
					"<div class=\"row\">" +
						"<div class=\"col-sm-11\"></div>" +
						"<button type=\"button\" id=\"closeAlertDataApertura\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
					"</div>" +
					"<div class=\"card-body\">" +
						"<h5 class=\"card-title\">";
						if(data.message.length)
							str += data.message;
						else
							str += "Inserimento data apertura non andato a buon fine!</h5>" ;
						str += "<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
					"</div>" +
				"</div>";
				
				//close alert message
				if ($("#fail_insert_data_apertura").length) { 
			 		$("button #closeAlertDataApertura").click( ()=> {
						$("#fail_insert_data_apertura").fadeOut(500);
					});
				}
				
				$("#div_msg_insert_data_apertura").append(str);
		
				$("#div_msg_insert_data_apertura").show(500);
	
			}
			
	})
	.fail(function(){
		$("#div_msg_insert_data_apertura").empty();
		
		let str =
		"<div id=\"fail_insert_data_apertura\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
			"<div class=\"row\">" +
				"<div class=\"col-sm-11\"></div>" +
				"<button type=\"button\" id=\"closeAlertDataApertura\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
			"</div>" +
			"<div class=\"card-body\">" +
				"<h5 class=\"card-title\">";
						if(data.message.length)
							str += data.message;
						else
							str += "Inserimento data apertura non andato a buon fine!</h5>" ;
						str += "<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
			"</div>" +
		"</div>";
		
		//close alert message
		if ($("#fail_insert_data_apertura").length) { 
	 		$("button #closeAlertDataApertura").click( ()=> {
				$("#fail_insert_data_apertura").fadeOut(500);
			});
		}
		
		$("#div_msg_insert_data_apertura").append(str);
	
		$("#div_msg_insert_data_apertura").show(500);
	});
	
}


/* FINE FUNZIONI OP ASP */

/* FUNZIONI MEDICO */


function trovaUtenteByCod(){

	$("#listaUtenti").empty();
	$("#sezioneTrovaCodSuccessResponse").hide();
	$("#div_fail_insert_lista_utenti").empty();
	$("#div_fail_insert_lista_utenti").hide();
	
	
	let codiceUtente = $("#codiceUtenteTrovaCod").val();
	
	if(codiceUtente=="" || !codiceUtente){
		event.preventDefault();
        event.stopPropagation();

		//aggiungo la classe errore/successo di bootstrap
		$("#trovaCodDiv").addClass('was-validated');
		return;
	}else{
		$("#trovaCodDiv").removeClass('was-validated');
	}
	
	let dataForm = {
		codiceUtente : codiceUtente
    };
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post("/googlielmo93/recupera_info_utente_by_cod", dataForm, () =>{loadSpinner()})
	.done(
		function(data) {
			loadSpinner(true);
			
			if(data.result == "success"){
			
				let str="";
			
				$("#listaUtenti").empty();
				
				
				let utentiATurnoNum = 0;
				
				if(data.data)
					utentiATurnoNum = data.data.length;
			
				$.each(data.data, function (index, value) {
					
					str="";
				
					let info_utente = value.info_utente;
			    
					if(info_utente!=undefined && info_utente!=""){
			    	
		    		str += "<div id=\"dip_"+info_utente.id_utente+"\" class=\"card border-info m-2\" style=\"width: 20rem; font-size:.92rem; cursor: pointer; ";
							if(index >= 6)
								str += "display : none!important;";
						 	str += "\">" +
		    				"<div class=\"card-body\">" +
		    				"<h5 class=\"card-title \">Codice Prenotazione<br>" +
		    					codiceUtente +
		    				"</h5>" +
		    				"<div class=\"col-md-12 my-5 text-center card_mod\" id=\"buttonSmntUtente\" data-toggle=\"modal\" data-target=\"#somministrazioneModale_"+info_utente.id_utente+"\" ><h5>Inserisci dati vaccino&nbsp;<i class=\"fas fa-chevron-circle-right\" style=\"color: #17a2b8!important;\"></i></h5></div>" +

		    					"<h6 class=\"text-center\">" +
		    						"Nome: " + info_utente.nome + "<br>" +
		    						"Cognome: "+ info_utente.cognome + "</br>" +
		    						"Codice Fiscale: " + info_utente.codice_fiscale +
		    					"</h6>" +
		    					
		    					"<div class=\"col-md-12 my-3 text-center card_mod\" data-toggle=\"modal\" data-target=\"#infoUtenteModale_"+info_utente.id_utente+"\" id=\"buttonVediInfo\"><h6>Vedi pi&ugrave; info <i class=\"fas fa-plus-square\" style=\"color: #17a2b8!important;\"></i></h6></div>" +
		    					
		    				"<div class=\"modal fade\" id=\"infoUtenteModale_"+info_utente.id_utente+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"infoUtenteExtraTable\" aria-hidden=\"true\">" +
		    					"<div class=\"modal-dialog modal-dialog-centered\" role=\"document\">" +
		    						"<div class=\"modal-content\">" +
		    							"<div class=\"modal-header\">" +
		    								"<h5 class=\"modal-title\" id=\"infoUtenteModaleTitle\">Informazioni utente</h5>" +
		    								"<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">" +
		    									"<span aria-hidden=\"true\">&times;</span>" +
		    								"</button>" +
		    							"</div>" +
		    							"<div class=\"modal-body\">" +
				    			      		"<div id=\"info_utente_div\">" +
						    					" <ul class=\"list-group\" style=\"text-align:	left!important;\">" +
							    					"<li class=\"list-group-item\">Nome: " + info_utente.nome + "</li>" +
							    					"<li class=\"list-group-item\">Cognome: " + info_utente.cognome + "</li>" +
							    					"<li class=\"list-group-item\">Email: " + info_utente.email + "</li>" +
							    					"<li class=\"list-group-item\">Sesso: " + info_utente.sesso + "</li>" +
							    					"<li class=\"list-group-item\">Codice Fiscale : " + info_utente.codice_fiscale + "</li>" +
							    					"<li class=\"list-group-item\">Data di nascita: " + info_utente.data_nascita + "</li>" +
							    					"<li class=\"list-group-item\">Regione di residenza: " + info_utente.regione + "</li>" +
							    					"<li class=\"list-group-item\">Provincia di residenza: " + info_utente.provincia + "</li>" +
							    					"<li class=\"list-group-item\">Comune di residenza: " + info_utente.citta + "</li>" +
								    			" </ul> " +
							    			"</div>" +
							    		"</div>" +
							    		"<div class=\"modal-footer\">" +
								    		"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Chiudi Info</button>" +
								    	"</div>" +
								    "</div>" +
								  "</div>" +
								"</div>" +
							"</div>" +
		    			"</div>";
		    			
		    			$("#listaUtenti").append(str);
		    			
		    			
		    			str="";
						
						
						str += "<div class=\"modal fade\" id=\"somministrazioneModale_"+info_utente.id_utente+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"somministrazioneExtraTable\" aria-hidden=\"true\">" +
	    					"<div class=\"modal-dialog modal-dialog-centered\" role=\"document\">" +
	    						"<div class=\"modal-content\">" +
	    							"<div class=\"modal-header\">" +
	    								"<h5 class=\"modal-title\" id=\"somministrazioneModaleTitle_"+info_utente.id_utente+"\">Inserisci i dati del vaccino</h5>" +
	    								"<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">" +
	    									"<span aria-hidden=\"true\">&times;</span>" +
	    								"</button>" +
	    							"</div>" +
	    							"<div class=\"modal-body\">" +
		    							
									"<div class=\"container mt-3 mb-5\">" +
							    		"<form class=\"m-5\" id=\"somministrazione_form_"+info_utente.id_utente+"\" role=\"form\" action='' method=\"post\" novalidate>" +
							    			"<div class=\"form-row mb-5\">" +
							    				"<div class=\"col-md-12 mb-3\" style=\"padding-right: 2%;\">" +
							    					"<label for=\"tipo_vaccino\">Tipo Vaccino</label>" +
							    						"<div class=\"input-group\">" +
							    							"<div class=\"input-group-prepend\">" +
							    								"<span class=\"input-group-text\" id=\"inputGroupPrepend_"+info_utente.id_utente+"\">Abc</span>" +
							    							"</div>" +
							    							"<input name=\"tipoVaccino\" type=\"text\" class=\"form-control\" id=\"tipoVaccino_"+info_utente.id_utente+"\"" +
							    								"placeholder=\"Inserisci il tipo di vaccino\"" +
							    								"aria-describedby=\"inputGroupPrepend_"+info_utente.id_utente+"\" required>" +
							    							"<div class=\"invalid-feedback\">" +
							    								"Inserisci il tipo di vaccino" +
							    							"</div>" +
							    						"</div>" +
							    				 "</div>" +
							    				 "<div class=\"col-md-12 my-3\" style=\"padding-left: 2%;\">" +
							    				 	"<label for=\"codiceVaccino\">Codice Vaccino</label>" +
							    				 	"<div class=\"input-group\">" +
							    				 		"<div class=\"input-group-prepend\">" +
							    				 			"<span class=\"input-group-text\" id=\"inputGroupPrepend_"+info_utente.id_utente+"\">Az123_</span>" +
							    				 		"</div>" +
							    				 		"<input name=\"codiceVaccino\" type=\"text\" class=\"form-control\" id=\"codiceVaccino_"+info_utente.id_utente+"\"" +
							    				 		"placeholder=\"Inserisci il codice del vaccino\" aria-describedby=\"inputGroupPrepend_"+info_utente.id_utente+"\" required>" +
							    				 		"<div class=\"invalid-feedback\">" +
							    				 			"Inserisci il codice del vaccino (il codice &egrave; visibile sulla fiala o sulla bolla di accompagnamento)" +
							    				 		"</div>" +
							    				 	"</div>" +
							    				 "</div>" +
							    				 "<div class=\"col-md-12 my-3\" style=\"padding-right: 2%;\">" +
							    				 "<label for=\"lotto\">Lotto</label>" +
							    				 "<div class=\"input-group\">" +
							    				 	"<div class=\"input-group-prepend\">" +
							    				 		"<span class=\"input-group-text\" id=\"inputGroupPrepend_"+info_utente.id_utente+"\">Az123_</span>" +
							    				 	"</div>" +
							    				 	"<input name=\"lotto\" type=\"text\" class=\"form-control\" id=\"lotto_"+info_utente.id_utente+"\"" +
							    				 	"placeholder=\"Inserisci il lotto della fiala\" aria-describedby=\"inputGroupPrepend_"+info_utente.id_utente+"\" required>" +
							    				 	"<div class=\"invalid-feedback\">" +
							    				 		"Inserisci il lotto di appartenenza della fiala (il codice lotto &egrave; visibile sulla bolla di accompagnamento)" +
							    				 	"</div>" +
							    				 "</div>" +
							    				 "</div>" +
							    			"</div>" +
							    			"<button type=\"submit\" class=\"mt-5 btn btn-primary btn-lg btn-block\" id=\"buttonConfirmSmnt_"+info_utente.id_utente+"\">Conferma dati somministrazione</button>" +
							    		"</form>" +
							    	"</div>" +
								    "</div>" +
								  "</div>" +
								"</div>" +
							"</div>";
						
								
						$("#listaUtenti").append(str);
		    			
			    }
					
					
					
					if($("#somministrazione_form_"+info_utente.id_utente).length){
						$("#somministrazione_form_"+info_utente.id_utente).submit(function(event) {
					        event.preventDefault();
					        event.stopPropagation();

					        //aggiungo la classe errore/successo di bootstrap
					        $("#somministrazione_form_"+info_utente.id_utente).addClass('was-validated');
					        
					        let tipoVacc = $("#tipoVaccino_"+info_utente.id_utente).val();
					        let CodeVacc = $("#codiceVaccino_"+info_utente.id_utente).val(); 
					        let lotto = $("#lotto_"+info_utente.id_utente).val();
					        
					        if($("#tipoVaccino_"+info_utente.id_utente)[0].checkValidity() && $("#codiceVaccino_"+info_utente.id_utente)[0].checkValidity()  && $("#lotto_"+info_utente.id_utente)[0].checkValidity()){
					        	somministraUtente(info_utente.id_utente, tipoVacc, CodeVacc, lotto, codiceUtente);
					        	 $("#somministrazioneModale_"+info_utente.id_utente).modal('hide');
					        }
						});
					}
				 
			});

			str="";
			
			
			
			if(utentiATurnoNum > 6){
				str += "<div class=\"col-md-10 my-5 text-center\" style=\"cursor:pointer\" id=\"buttonVediAltri\"><h5>Vedi Altri</h5></div>";
			}
			
			
			$("#listaUtenti").append(str);
			$("#sezioneTrovaCodSuccessResponse").show(500);
			
			if(utentiATurnoNum == 0){
				$("#sezioneTrovaCodSuccessResponse").hide(500);
			}
			
			
			if(utentiATurnoNum > 6){
				
				$("#buttonVediAltri").click(
					function(){
						let count = 0;
						$("#listaUtenti").find(".card").each(
							(i, el) => {
								if($(el).is(":hidden")){
									$("#buttonVediAltri").hide();
									
									if(count >= 6){
										$("#buttonVediAltri").show();
										return;
									}
								
									count++;
									$(el).show();
								}
							}
						);
						
					}
				);
			}
			
		}else{
			
			let str = 
			"<br><br><div id=\"fail_insert_smnt\" class=\"card border-danger m-2 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
				"<div class=\"card-body\">" +
					"<h5  style=\"font-weight: bold;\" class=\"card-title\">Recupero dei dati dell'utente non andato a buon fine!</h5>";
					if(Object.keys(data.message).length){
						str += "<h6 style=\"font-weight: bold;\">"+data.message+"</h6><br>";
					}
					str += "<h6>Si prega di inserire un codice prenotazione valido per la giornata corrente,<br> se il problema sussiste la preghiamo di contattarci.</h6>" +
				"</div>" +
			"</div>";
			
			$("#div_fail_insert_lista_utenti").empty();
			$("#div_fail_insert_lista_utenti").append(str);
			$("#div_fail_insert_lista_utenti").show(500);
		}
			
	})
	.fail(function(){
			let str = 
			"<br><br><div id=\"fail_insert_smnt\" class=\"card border-danger m-2 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
				"<div class=\"card-body\">" +
					"<h5  style=\"font-weight: bold;\" class=\"card-title\">Recupero dei dati dell'utente non andato a buon fine!</h5>";
					if(Object.keys(data.message).length){
						str += "<h6 style=\"font-weight: bold;\">"+data.message+"</h6><br>";
					}
					str += "<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
				"</div>" +
			"</div>";
			
			$("#div_fail_insert_lista_utenti").empty();
			$("#div_fail_insert_lista_utenti").append(str);
			$("#div_fail_insert_lista_utenti").show(500);
    });
}


function listaUtentiATurno(operation){

	$("#listaUtenti").empty();
	$("#sezioneTrovaCodSuccessResponse").hide();
	$(".HiddenListTurno").hide();
	$("#div_fail_insert_lista_utenti").empty();
	$("#div_fail_insert_lista_utenti").hide();
	$("#div_fail_smnt_lista_utenti").empty();
	$("#div_fail_smnt_lista_utenti").hide();
	
	
	let dataForm = {
		operation : operation
    };
	
		
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post("/googlielmo93/recupera_list_turno", dataForm, () =>{loadSpinner()})
	.done(
		function(data) {
			loadSpinner(true);
			
			if(data.result == "success"){
			
				let str="";
			
				$("#listaUtenti").empty();
				
				
				let utentiATurnoNum = 0;
				
				if(data.data)
					utentiATurnoNum = data.data.length;
			
				$.each(data.data, function (index, value) {
					
					str="";
				
					let info_utente = value.lista_utenti.info_utente;
					let info_prenotazione = value.lista_utenti.info_prenotazione;
			    
					if(info_utente!=undefined && info_utente!=""){
			    	
		    		str += "<div id=\"dip_"+info_utente.id_utente+"\" class=\"card border-info m-2\" style=\"width: 20rem; font-size:.92rem; cursor: pointer; ";
							if(index >= 6)
								str += "display : none!important;";
						 	str += "\">" +
		    				"<div class=\"card-body\">" +
		    				"<h5 class=\"card-title \">Codice Prenotazione<br>" +
		    					info_prenotazione.codice_prenotazione +
		    				"</h5>" +
		    				"<input type=\"hidden\" id=\"codPrenotazione_"+info_utente.id_utente+"\" value=\""+info_prenotazione.codice_prenotazione+"\">" +
		    				"<div class=\"col-md-12 my-5 text-center card_mod\" id=\"buttonSmntUtente\" data-toggle=\"modal\" data-target=\"#somministrazioneModale_"+info_utente.id_utente+"\" ><h5>Inserisci dati vaccino&nbsp;<i class=\"fas fa-chevron-circle-right\" style=\"color: #17a2b8!important;\"></i></h5></div>" +

		    					"<h6 class=\"text-center\">" +
		    						"Nome: " + info_utente.nome + "<br>" +
		    						"Cognome: "+ info_utente.cognome + "</br>" +
		    						"Codice Fiscale: " + info_utente.codice_fiscale +
		    					"</h6>" +
		    					
		    					"<div class=\"col-md-12 my-3 text-center card_mod\" data-toggle=\"modal\" data-target=\"#infoUtenteModale_"+info_utente.id_utente+"\" id=\"buttonVediInfo\"><h6>Vedi pi&ugrave; info <i class=\"fas fa-plus-square\" style=\"color: #17a2b8!important;\"></i></h6></div>" +
		    					
		    				"<div class=\"modal fade\" id=\"infoUtenteModale_"+info_utente.id_utente+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"infoUtenteExtraTable\" aria-hidden=\"true\">" +
		    					"<div class=\"modal-dialog modal-dialog-centered\" role=\"document\">" +
		    						"<div class=\"modal-content\">" +
		    							"<div class=\"modal-header\">" +
		    								"<h5 class=\"modal-title\" id=\"infoUtenteModaleTitle\">Informazioni utente</h5>" +
		    								"<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">" +
		    									"<span aria-hidden=\"true\">&times;</span>" +
		    								"</button>" +
		    							"</div>" +
		    							"<div class=\"modal-body\">" +
				    			      		"<div id=\"info_utente_div\">" +
						    					" <ul class=\"list-group\" style=\"text-align:	left!important;\">" +
							    					"<li class=\"list-group-item\">Nome: " + info_utente.nome + "</li>" +
							    					"<li class=\"list-group-item\">Cognome: " + info_utente.cognome + "</li>" +
							    					"<li class=\"list-group-item\">Email: " + info_utente.email + "</li>" +
							    					"<li class=\"list-group-item\">Sesso: " + info_utente.sesso + "</li>" +
							    					"<li class=\"list-group-item\">Codice Fiscale : " + info_utente.codice_fiscale + "</li>" +
							    					"<li class=\"list-group-item\">Data di nascita: " + info_utente.data_nascita + "</li>" +
							    					"<li class=\"list-group-item\">Regione di residenza: " + info_utente.regione + "</li>" +
							    					"<li class=\"list-group-item\">Provincia di residenza: " + info_utente.provincia + "</li>" +
							    					"<li class=\"list-group-item\">Comune di residenza: " + info_utente.citta + "</li>" +
								    			" </ul> " +
							    			"</div>" +
							    		"</div>" +
							    		"<div class=\"modal-footer\">" +
								    		"<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">Chiudi Info</button>" +
								    	"</div>" +
								    "</div>" +
								  "</div>" +
								"</div>" +
							"</div>" +
		    			"</div>";
		    			
		    			$("#listaUtenti").append(str);
		    			
		    			
		    			str="";
						
						
						str += "<div class=\"modal fade\" id=\"somministrazioneModale_"+info_utente.id_utente+"\" tabindex=\"-1\" role=\"dialog\" aria-labelledby=\"somministrazioneExtraTable\" aria-hidden=\"true\">" +
	    					"<div class=\"modal-dialog modal-dialog-centered\" role=\"document\">" +
	    						"<div class=\"modal-content\">" +
	    							"<div class=\"modal-header\">" +
	    								"<h5 class=\"modal-title\" id=\"somministrazioneModaleTitle_"+info_utente.id_utente+"\">Inserisci i dati del vaccino</h5>" +
	    								"<button type=\"button\" class=\"close\" data-dismiss=\"modal\" aria-label=\"Close\">" +
	    									"<span aria-hidden=\"true\">&times;</span>" +
	    								"</button>" +
	    							"</div>" +
	    							"<div class=\"modal-body\">" +
		    							
									"<div class=\"container mt-3 mb-5\">" +
							    		"<form class=\"m-5\" id=\"somministrazione_form_"+info_utente.id_utente+"\" role=\"form\" action='' method=\"post\" novalidate>" +
							    			"<div class=\"form-row mb-5\">" +
							    				"<div class=\"col-md-12 mb-3\" style=\"padding-right: 2%;\">" +
							    					"<label for=\"tipo_vaccino\">Tipo Vaccino</label>" +
							    						"<div class=\"input-group\">" +
							    							"<div class=\"input-group-prepend\">" +
							    								"<span class=\"input-group-text\" id=\"inputGroupPrepend_"+info_utente.id_utente+"\">Abc</span>" +
							    							"</div>" +
							    							"<input name=\"tipoVaccino\" type=\"text\" class=\"form-control\" id=\"tipoVaccino_"+info_utente.id_utente+"\"" +
							    								"placeholder=\"Inserisci il tipo di vaccino\"" +
							    								"aria-describedby=\"inputGroupPrepend_"+info_utente.id_utente+"\" required>" +
							    							"<div class=\"invalid-feedback\">" +
							    								"Inserisci il tipo di vaccino" +
							    							"</div>" +
							    						"</div>" +
							    				 "</div>" +
							    				 "<div class=\"col-md-12 my-3\" style=\"padding-left: 2%;\">" +
							    				 	"<label for=\"codiceVaccino\">Codice Vaccino</label>" +
							    				 	"<div class=\"input-group\">" +
							    				 		"<div class=\"input-group-prepend\">" +
							    				 			"<span class=\"input-group-text\" id=\"inputGroupPrepend_"+info_utente.id_utente+"\">Az123_</span>" +
							    				 		"</div>" +
							    				 		"<input name=\"codiceVaccino\" type=\"text\" class=\"form-control\" id=\"codiceVaccino_"+info_utente.id_utente+"\"" +
							    				 		"placeholder=\"Inserisci il codice del vaccino\" aria-describedby=\"inputGroupPrepend_"+info_utente.id_utente+"\" required>" +
							    				 		"<div class=\"invalid-feedback\">" +
							    				 			"Inserisci il codice del vaccino (il codice &egrave; visibile sulla fiala o sulla bolla di accompagnamento)" +
							    				 		"</div>" +
							    				 	"</div>" +
							    				 "</div>" +
							    				 "<div class=\"col-md-12 my-3\" style=\"padding-right: 2%;\">" +
							    				 "<label for=\"lotto\">Lotto</label>" +
							    				 "<div class=\"input-group\">" +
							    				 	"<div class=\"input-group-prepend\">" +
							    				 		"<span class=\"input-group-text\" id=\"inputGroupPrepend_"+info_utente.id_utente+"\">Az123_</span>" +
							    				 	"</div>" +
							    				 	"<input name=\"lotto\" type=\"text\" class=\"form-control\" id=\"lotto_"+info_utente.id_utente+"\"" +
							    				 	"placeholder=\"Inserisci il lotto della fiala\" aria-describedby=\"inputGroupPrepend_"+info_utente.id_utente+"\" required>" +
							    				 	"<div class=\"invalid-feedback\">" +
							    				 		"Inserisci il lotto di appartenenza della fiala (il codice lotto &egrave; visibile sulla bolla di accompagnamento)" +
							    				 	"</div>" +
							    				 "</div>" +
							    				 "</div>" +
							    			"</div>" +
							    			"<button type=\"submit\" class=\"mt-5 btn btn-primary btn-lg btn-block\" id=\"buttonConfirmSmnt_"+info_utente.id_utente+"\">Conferma dati somministrazione</button>" +
							    		"</form>" +
							    	"</div>" +
								    "</div>" +
								  "</div>" +
								"</div>" +
							"</div>";
						
								
						$("#listaUtenti").append(str);
		    			
			    }
					
					
					
					if($("#somministrazione_form_"+info_utente.id_utente).length){
						$("#somministrazione_form_"+info_utente.id_utente).submit(function(event) {
					        event.preventDefault();
					        event.stopPropagation();

					        //aggiungo la classe errore/successo di bootstrap
					        $("#somministrazione_form_"+info_utente.id_utente).addClass('was-validated');
					        
					        let tipoVacc = $("#tipoVaccino_"+info_utente.id_utente).val();
					        let CodeVacc = $("#codiceVaccino_"+info_utente.id_utente).val(); 
					        let lotto = $("#lotto_"+info_utente.id_utente).val();
					        let codPrenotazione = $("#codPrenotazione_"+info_utente.id_utente).val();
					        
					        if($("#tipoVaccino_"+info_utente.id_utente)[0].checkValidity() && $("#codiceVaccino_"+info_utente.id_utente)[0].checkValidity()  && $("#lotto_"+info_utente.id_utente)[0].checkValidity()){
					        	somministraUtente(info_utente.id_utente, tipoVacc, CodeVacc, lotto, codPrenotazione);
					        	 $("#somministrazioneModale_"+info_utente.id_utente).modal('hide');
					        }
						});
					}
				 
			});

			str="";
			
			if(utentiATurnoNum > 6){
				str += "<div class=\"col-md-10 my-5 text-center\" style=\"cursor:pointer\" id=\"buttonVediAltri\"><h5>Vedi Altri</h5></div>";
			}
			
			
			$("#listaUtenti").append(str);

			$(".HiddenListTurno").show();
			$("#sezioneTrovaCodSuccessResponse").show(500);
			
			
			if(utentiATurnoNum > 6){
				
				$("#buttonVediAltri").click(
					function(){
						let count = 0;
						$("#listaUtenti").find(".card").each(
							(i, el) => {
								if($(el).is(":hidden")){
									$("#buttonVediAltri").hide();
									
									if(count >= 6){
										$("#buttonVediAltri").show();
										return;
									}
								
									count++;
									$(el).show();
								}
							}
						);
						
					}
				);
			}
			
		}else{
			
			let str = 
			"<br><br><div id=\"fail_insert_smnt\" class=\"card border-danger m-2 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
				"<div class=\"card-body\">" +
					"<h5  style=\"font-weight: bold;\" class=\"card-title\">Lista utenti non disponibile!</h5>";
					if(Object.keys(data.message).length){
						str += "<h6 style=\"font-weight: bold;\">"+data.message+"</h6><br>";
					}
					str += "<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
				"</div>" +
			"</div>";
			
			$("#div_fail_smnt_lista_utenti").empty();
			$("#div_fail_smnt_lista_utenti").append(str);
			$(".HiddenListTurno").hide();
			$("#div_fail_smnt_lista_utenti").show(500);
			$("#sezioneTrovaCodSuccessResponse").show(500);
		}
			
	})
	.fail(function(){
			let str = 
			"<br><br><div id=\"fail_insert_smnt\" class=\"card border-danger m-2 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
				"<div class=\"card-body\">" +
					"<h5  style=\"font-weight: bold;\" class=\"card-title\">Lista utenti non disponibile!</h5>";
					if(Object.keys(data.message).length){
						str += "<h6 style=\"font-weight: bold;\">"+data.message+"</h6><br>";
					}
					str += "<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
				"</div>" +
			"</div>";
			
			$("#div_fail_smnt_lista_utenti").empty();
			$("#div_fail_smnt_lista_utenti").append(str);
			$(".HiddenListTurno").hide();
			$("#div_fail_smnt_lista_utenti").show(500);
			$("#sezioneTrovaCodSuccessResponse").show(500);
    });
}


function somministraUtente(id_utente, tipo, codiceVaccino, lotto, codicePrenotazione){

	let dataForm = {
		id_utente : id_utente,
		tipo : tipo,
		codice_vaccino : codiceVaccino,
		lotto : lotto,
		codice_prenotazione : codicePrenotazione
	};

	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post(
		"/googlielmo93/somministra_vaccino", dataForm, () =>{loadSpinner()}
	)
	.done(
		function(data) {
			loadSpinner(true);
			
			if(data.result && Object.keys(data.result).length && data.result == "success"){
				
				if($("#dip_"+id_utente).length){
					
					$("#dip_"+id_utente+" .card-body").css("opacity", "0.2");
					
					let str = "<div class=\"col-sm-12 mx-auto my-auto\">" +
								"<i class=\"far fa-check-circle\" style=\"color:#66cdaa; font-size: 5rem;\"></i>" +
							  "</div>";
					
					$("#dip_"+id_utente).append(str);
					
					setTimeout(function () {
						$("#listaUtenti #dip_"+id_utente).remove();
						
						if($("#listaUtenti").children().length == 0){
							
							$("#div_fail_smnt_lista_utenti").empty();
							
							let str =
							"<div id=\"fail_insert_smnt\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
								"<div class=\"card-body\">" +
									"<h5 class=\"card-title\">Nessun utente a turno in questo centro vaccinale</h5>" +
								"</div>" +
							"</div>";
							
							$("#div_fail_smnt_lista_utenti").append(str);
					
							$("#div_fail_smnt_lista_utenti").show(500);
						}
						
						$("#tipoVaccino_"+id_utente).empty();
				        $("#codiceVaccino_"+id_utente).empty(); 
				        $("#lotto_"+id_utente).empty();
				        
				        $("#div_fail_smnt_lista_utenti").empty();
						$("#div_fail_smnt_lista_utenti").append(str);
						$("#sezioneTrovaCod").hide(500);
						
					}, 750);
					

					if($("#listaUtenti").children().length == 1){
						$(".HiddenListTurno").hide();
					}
					
					
				}
				

			}else{
				
				$("#div_fail_smnt_lista_utenti").empty();
				
				let str =
				"<div id=\"fail_insert_smnt\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
					"<div class=\"row\">" +
						"<div class=\"col-sm-11\"></div>" +
						"<button type=\"button\" id=\"closeAlertSmnt\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
					"</div>" +
					"<div class=\"card-body\">" +
						"<h5 class=\"card-title\">Conferma somministrazione non andata a buon fine!</h5>" +
						"<h6 class=\"card-title\">"+data.message+"</h6>" +
						"<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
					"</div>" +
				"</div>";
				
				//close alert message
				if ($("#fail_insert_smnt").length) { 
			 		$("button #closeAlertSmnt").click( ()=> {
						$("#fail_insert_smnt").fadeOut(500);
					});
				}
				
				$("#div_fail_smnt_lista_utenti").empty();
				$("#div_fail_smnt_lista_utenti").append(str);
				$(".HiddenListTurno").hide();
				$("#div_fail_smnt_lista_utenti").show(500);
				$("#sezioneTrovaCodSuccessResponse").show(500);
			}
			
	})
	.fail(function(){
		
		$("#div_fail_smnt_lista_utenti").empty();
		
		let str =
		"<div id=\"fail_insert_smnt\" class=\"card border-danger col-sm-12 m-2 pb-3 mb-3 mx-auto alert alert-danger\" style=\"width: 100%; font-size:.92rem;\" >" +
			"<div class=\"row\">" +
				"<div class=\"col-sm-11\"></div>" +
				"<button type=\"button\" id=\"closeAlertSmnt\" class=\"close col-sm-1\" style=\"float: right!important;\" data-dismiss=\"alert\">x</button>" +
			"</div>" +
			"<div class=\"card-body\">" +
				"<h5 class=\"card-title\">Conferma somministrazione non andata a buon fine!</h5>" +
				"<h6 class=\"card-title\">"+data.message+"</h6>" +
				"<h6>Si prega di riprovare, se il problema sussiste la preghiamo di contattarci.</h6>" +
			"</div>" +
		"</div>";
		
		//close alert message
		if ($("#fail_insert_smnt").length) { 
	 		$("button #closeAlertSmnt").click( ()=> {
				$("#fail_insert_smnt").fadeOut(500);
			});
		}
		
		$("#div_fail_smnt_lista_utenti").empty();
		$("#div_fail_smnt_lista_utenti").append(str);
		$(".HiddenListTurno").hide();
		$("#div_fail_smnt_lista_utenti").show(500);
		$("#sezioneTrovaCodSuccessResponse").show(500);
	});
	
}

/* FINE FUNZIONI MEDICO */


/* FUNZIONI TURNO */
function tabellone(operation, subOperation="", id_centro_vaccinale=-1){
	
	let dataForm = {
		operation : operation,
		sub_operation : subOperation,
		id_centro_vaccinale
	};
	
	//dataForm = PlainObject (JS Obj)
	//$.post accetta o PlainObject o Stringhe non JSON String
	$.post("/googlielmo93/tabellone", dataForm, () =>{loadSpinner()})
	.done(
		function(data) {
			loadSpinner(true);
			
			if(data.result == "success"){
			
				let str="";
				
				$.each(data.data, function (index, value) {
					let posti_disponibili = value.posti_disponibili;
					let numero_tabellone = value.numero_tabellone;
					
					$("#infoSalaAttesa").show();
					$("#posti_disponibili").empty();
					$("#numero_turno").empty();
					$("#posti_disponibili").html("<span style=\"text-align:center!important\">Posti disponibili:&nbsp;"+posti_disponibili+"</span>");
					$("#numero_turno").html("<span style=\"text-align:center!important\">Numero Turno:&nbsp;"+numero_tabellone+"</span>");
				});
	
			
		}else{
			$("#infoSalaAttesa").show();
			$("#posti_disponibili").empty();
			$("#numero_turno").empty();
			$("#posti_disponibili").html("<span style=\"text-align:center!important\">Posti disponibili:&nbsp;Non disponibile</span>");
			$("#numero_turno").html("<span style=\"text-align:center!important\">Numero Turno:&nbsp;Non disponibile</span>");
		}
			
	})
	.fail(function(){
		$("#infoSalaAttesa").show();
		$("#posti_disponibili").empty();
		$("#numero_turno").empty();
		$("#posti_disponibili").html("<span style=\"text-align:center!important\">Posti disponibili:&nbsp;Non disponibile</span>");
		$("#numero_turno").html("<span style=\"text-align:center!important\">Numero Turno:&nbsp;Non disponibile</span>");
	});
	
}


function loadSelectTabellone(){

	if ($("#tabellone_turno").length) {
		
		$("#regioneTabellone").empty();
		$("#provinciaTabellone").empty();
		$("#comuneTabellone").empty();
		$("#div_fail_insert_tabellone").empty();
		$("#div_fail_insert_tabellone").hide();
		
		$(".HideInputProvincia").hide();
		$(".HideInputComune").hide();
		$(".HideInputCentroVaccinale").hide();
		
		$("#sezioneFormTabellone").show(500);

	    $("#text-scelta-tabellone").text("Seleziona la regione");
	    
	    
		//carico le regioni
		loadRegioniGenerico("#regioneTabellone");
		
		// al change sulla select regione fa il load delle corrispettive province nella select province
		$("#regioneTabellone").change(function(){
			
			$("#provinciaTabellone").empty();
			$("#comuneTabellone").empty();
			$("#centroVaccinaleTabellone").empty();
			$("#text-scelta-tabellone").empty();
			$("#div_fail_insert_tabellone").empty();
			$("#div_fail_insert_tabellone").hide();
			
			//nascondi
			$(".HideInputComune").hide();
			$(".HideInputCentroVaccinale").hide();
			
			//mostra la select provincia
			$(".HideInputProvincia").show(500);
		    $("#text-scelta-tabellone").text("Seleziona la provincia");
		    
		    //carica province in base alla regione passata
		    loadProvinceGenerico("#provinciaTabellone", $("#regioneTabellone option:selected").val());
		    
		});

		
		
		// al change sulla select province fa il load sulla select dei comuni corrispondenti alla provincia,
		// e inoltre fa il load dei centri vaccinali nella provincia
		$("#provinciaTabellone").change(function(){
			$("#comuneTabellone").empty();
			$("#centroVaccinaleTabellone").empty();
			$("#text-scelta-tabellone").empty();
			$("#div_fail_insert_tabellone").empty();
			$("#div_fail_insert_tabellone").hide();

			//mostra
			$(".HideInputComune").show(500);
			$(".HideInputCentroVaccinale").show(500);
		    $("#text-scelta-tabellone").text("Seleziona il centro vaccinale o specifica il comune");
		    
		    // carico i centri vaccinali in base alla regione, alla provincia
		    loadListCentriVaccinaliGenerico("#centroVaccinaleTabellone", $("#regioneTabellone option:selected").val(), $("#provinciaTabellone option:selected").val());
		    
		    // carico i comuni in base alla regione e alla provincia selezionata
			loadComuniGenerico("#comuneTabellone ", $("#regioneTabellone option:selected").val(), $("#provinciaTabellone option:selected").val())
		})

		
		$("#comuneTabellone").change(function(){
			$("#centroVaccinaleTabellone").empty();
			$("#text-scelta-tabellone").empty();
			$("#div_fail_insert_tabellone").empty();
			$("#div_fail_insert_tabellone").hide();
			
			//mostra
			$(".HideInputCentroVaccinale").show(500);
		    $("#text-scelta-tabellone").text("Seleziona il centro vaccinale");
		    
		    loadListCentriVaccinaliGenerico("#centroVaccinaleTabellone", $("#regioneTabellone option:selected").val(), $("#provinciaTabellone option:selected").val(), $("#comuneTabellone option:selected").val());
		
		})
		
		let interval = null;
		
		$("#centroVaccinaleTabellone").change(function(){
			$("#text-scelta-tabellone").empty();
			$("#div_fail_insert_tabellone").empty();
			$("#div_fail_insert_tabellone").hide();
			
			//mostra
			$("#sezioneFormTabellone").hide(500);
		    
			if(interval != null)
				clearInterval(interval);
			
			tabellone("tabellone_by_id_centro_vaccinale", "", $("#centroVaccinaleTabellone option:selected").val());
			interval = setInterval(()=>{
				tabellone("tabellone_by_id_centro_vaccinale", "", $("#centroVaccinaleTabellone option:selected").val());
			}, 5000);
			
		})
		
		
	}
}



/* FINE FUNZIONI TURNO */
