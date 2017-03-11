function createChips(name){
console.log(name);
padre = document.getElementById("boxRedes");
hijo = document.createElement('div');
hijo.className="chip";
hijo.innerHTML = name+"<i class='close material-icons'>close</i>";
console.log(hijo.innerHTML);
padre.appendChild(hijo);
padre2 = document.getElementById("boxRedes2");
hijo2 = document.createElement('div');
hijo2.className="chip";
hijo2.innerHTML = name+"<i class='close material-icons'>close</i>";
padre2.appendChild(hijo2);
}
function createChips2(){
	name = document.getElementById('filtros').value;
	name2 = document.getElementById('filtros2').value;
if(name!="" ){
	padre = document.getElementById("boxTags");

hijo = document.createElement('div');
hijo.className="chip";

hijo.innerHTML = name+"<i class='close material-icons'>close</i>";
console.log(hijo.innerHTML);
padre.appendChild(hijo);
}
if(name2!=""){
padre2 = document.getElementById("boxTags2");
hijo2 = document.createElement('div');
hijo2.className="chip";

hijo2.innerHTML = name2+"<i class='close material-icons'>close</i>";
padre2.appendChild(hijo2);
}
}
function contador(){
 contador1 = document.getElementById('textarea1').value;

 if(contador1.length < 140){
 	//return 0
 	console.log(0);
 }
 else{
 	//return 1
 	console.log(1);
 }
}

