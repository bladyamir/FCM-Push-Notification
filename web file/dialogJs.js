
	var box = document.getElementById('dialogBox');
	var list = document.getElementById('list');
	var chooseBtn = document.getElementById("choose");
	var isElementCreated=false;
	var userSize;
	var isCustom;
	var sql;

	function createElement(type){
		return  element=document.createElement(""+type);
	}

	function setAttribute(element,id,type,value){
		if(id != null){
			element.id=id;
		}
		if(type != null){
			element.type=type;
		}
		if(value != null){
			element.value=value;
		}

		
		return element;
	}

	function createButton(id,text){
		var element=document.createElement("button");
		element.id=id;
		element.type="button";

		var text=document.createTextNode(text);
		element.appendChild(text);

		return element;
	}

	function createItems(jsonData,i){
		if(isElementCreated == false){
			
			var listItem=createElement("Li");

			var checkBox=createElement("input");
			var id=jsonData.data[i]["id"];
			checkBox=setAttribute(checkBox,"c"+i,"checkbox",id);
	




			var lavel=createElement("label");
			lavel=setAttribute(lavel,"l"+i,null,null);
			var name=jsonData.data[i]["name"];
			var labelText=document.createTextNode(''+name);

			lavel.appendChild(labelText);
			listItem.appendChild(checkBox);
			listItem.appendChild(lavel);
			list.appendChild(listItem);


			

		}
		


	}

	

	function analysisCheckBox(){
		sql="select token from token_table";
		for(var i=0;i < userSize;i++){
			var checkBox=document.getElementById("c"+i);
			if(checkBox.checked == true && isCustom == false){
				sql=sql+" where id in (";
				isCustom=true;
			}

			if(isCustom && checkBox.checked){
				sql=sql+""+checkBox.value+",";
			}

			

		}

		if(isCustom){
			sql=sql.substring(0, sql.length-1)+")";
		}

		

		
	}

	

	function ajaxResponse(jsonData){
		userSize=jsonData.data.length;
		
		for (var i = 0; i < userSize; i++) {
			createItems(jsonData,i);
		}
		
		if(isElementCreated == false){
			createButton();
			var listItem2=createElement("Li");

			var okButton=createButton("ok","OK");
			okButton.onclick=function(){
				analysisCheckBox();
				box.style.display="none";
			}

			var cencelBtton=createButton("cencel","cencel");
			cencelBtton.onclick=function(){
				box.style.display="none";
			}


			listItem2.appendChild(okButton);
			listItem2.appendChild(cencelBtton);
			list.appendChild(listItem2);
		}
		

		isElementCreated=true;

		box.style.display = "block";
		
		 
		


	}


	function ajaxRequest(){
		if(window.XMLHttpRequest){
			var ajax=new XMLHttpRequest();

			

		}else {
			var ajax =new ActiveXObject("Microsoft.XMLHTTP");
		}

		ajax.onreadystatechange = function(){
			if(ajax.readyState == 4 && ajax.status == 200){
				
				var jsonObj=JSON.parse(ajax.responseText);
				ajaxResponse(jsonObj);
			}
			
			

			
		}
		ajax.open("GET","list.php",true);
		ajax.send();



	}


	chooseBtn.onclick = function() {
		isCustom=false;
		ajaxRequest();
		
	}

		// choose button is not clicked
				if(sql == null){
					sql="select token from token_table";
				}
				document.getElementById("sql").value=sql;

	





	window.onclick = function(event) {

	    if (event.target == box) {
	        box.style.display = "none";
	    }
	}