var iconMap = new Array();
var iconList = new Array( iconMap );

function Toggle(item)
{
	var idx = -1;
	for( i = 0; i < iconList.length; i++ )
	{
		if( iconList[i][0] == item )
		{
			idx = i;
			break;
		}
	}
	
	if( idx < 0 )
		alert( "Could not find key in Icon List." );
		   
	var div=document.getElementById("D"+item);
	var visible=(div.style.display!="none");
	var key=document.getElementById("P"+item);	
	
	
	var removeIcon = div.hasChildNodes() == false;
	
	if( key != null )
	{
		if( !removeIcon )
		{
			if (visible)
			{
		 		div.style.display="none";
		 		key.innerHTML="<img src='images/tree/plus.gif' width='16' height='16' hspace='0' vspace='0' border='0'>";
			}
			else
			{
		  		div.style.display="block";
				key.innerHTML="<img src='images/tree/minus.gif' width='16' height='16' hspace='0' vspace='0' border='0'>";
			}
		}
		else
			key.innerHTML="<img src='images/tree/minus.gif' width='16' height='16' hspace='0' vspace='0' border='0'>";
	}
	
	key=document.getElementById("I"+item);
	if( key != null )
	{
		if (visible)
		{
	 		div.style.display="none";
	 		key.innerHTML="<img src='"+iconList[idx][1]+"' width='16' height='16' hspace='0' vspace='0' border='0'>";
		}
		else
		{
	  		div.style.display="block";
			key.innerHTML="<img src='"+iconList[idx][2]+"' width='16' height='16' hspace='0' vspace='0' border='0'>";
		}
	}	
}

function AddChildAndToggle(item){
	var xmlhttp;	 
	xmlhttp=getXMLRequester();	
	if(xmlhttp){	
		xmlhttp.onreadystatechange = function doAction() {		
        	if (xmlhttp.readyState == 4)
			{			
				/*alert(xmlhttp.responseText);*/				
				var response = xmlhttp.responseXML.documentElement;
				var classidList = response.getElementsByTagName('category');				
				for(var i=0;i<classidList.length;i++){
					xx=classidList[i].getElementsByTagName('id');
					yy=classidList[i].getElementsByTagName('title');
					classid=xx[0].firstChild.data
					classtitle=yy[0].firstChild.data				
					var Parentdiv=document.getElementById("D"+item);
					var thisdiv=document.getElementById("D"+classid);
					if(thisdiv==null){
						addChild(Parentdiv,classid,classtitle);						
					}
				}
        	}
		};
		var ReturnValue=xmlhttp.open("POST", "category_getchilds.jsp?classid="+item,true);
		xmlhttp.send("");
	}
	Toggle(item);
}


function Expand() {
   divs=document.getElementsByTagName("DIV");
   for (i=0;i<divs.length;i++) {
	 divs[i].style.display="block";
	 key=document.getElementById("x" + divs[i].id);
	 key.innerHTML="<img src='images/tree/textfolder.gif' width='16' height='16' hspace='0' vspace='0' border='0'>";
   }
}

function Collapse() {
   divs=document.getElementsByTagName("DIV");
   for (i=0;i<divs.length;i++) {
	 divs[i].style.display="none";
	 key=document.getElementById("x" + divs[i].id);
	 key.innerHTML="<img src='images/tree/folder.gif' width='16' height='16' hspace='0' vspace='0' border='0'>";
   }
}

function AddImage( parent, imgFileName )
{
	img=document.createElement("img");
	img.setAttribute( "src", imgFileName );
	img.setAttribute( "width", 16 );
	img.setAttribute( "height", 16 );
	img.setAttribute( "hspace", 0 );
	img.setAttribute( "vspace", 0 );
	img.setAttribute( "border", 0 );
	parent.appendChild(img);
}

function CreateUniqueTagName( seed )
{
	var tagName = seed;
	var attempt = 0;
	
	if( tagName == "" || tagName == null )
		tagName = "0";

	while( document.getElementById(tagName) != null )
	{
		tagName = "x" + tagName;
		if( attempt++ > 50 )
		{
			alert( "Cannot create unique tag name. Giving up. \nTag = " + tagName );
			break;
		}
	}
	
	return tagName;
}

function CreateTreeItem( parent, img1FileName, img2FileName,nodeId, nodeName, url, target,isManage)
{
	var uniqueId = CreateUniqueTagName( nodeId );
	for( i=0; i < iconList.length; i++ )
		if( iconList[i][0] == uniqueId )
		{
			alert( "Non unique ID in Element Map. '" + uniqueId + "'" );
			// return;
		}
	iconList[iconList.length] = new Array( uniqueId, img1FileName, img2FileName );

	table = document.createElement("TABLE");
	if( parent != null )
		parent.appendChild( table );

	table.setAttribute( "border", 0 );
	table.setAttribute( "cellpadding", 1 );
	table.setAttribute( "cellspacing", 1 );
		
	tablebody = document.createElement("TBODY");
	table.appendChild(tablebody);
		
   	row=document.createElement("TR");
	tablebody.appendChild( row );
	
	cell=document.createElement("TD");
	cell.setAttribute( "width", 16 );
	row.appendChild(cell);	
		
	a=document.createElement("A");
	cell.appendChild( a );
	a.setAttribute( "id", "P"+uniqueId );
	if (uniqueId==0)
	{
		a.setAttribute( "href", "javascript:Toggle(\""+uniqueId+"\");" );
	}
	else{
		a.setAttribute( "href", "javascript:AddChildAndToggle(\""+uniqueId+"\");" );
	}	
	AddImage( a, "images/tree/plus.gif" );	
	
	cell=document.createElement("TD");
	cell.setAttribute( "width", 16 );
	row.appendChild(cell);		
	
	a=document.createElement("A");
	a.setAttribute( "id", "I"+uniqueId );
	a.setAttribute( "href", "javascript:Toggle(\""+uniqueId+"\");" );
	cell.appendChild(a);
	
	AddImage( a, img1FileName );
	
	cell=document.createElement("TD");
	cell.noWrap = true;
	a=document.createElement("A");
	a.setAttribute( "id","U"+uniqueId );
	cell.appendChild( a );
	if( url != null )
	{
		a.setAttribute( "href", url );
		if( target != null )
			a.setAttribute( "target", target );
		else
			a.setAttribute( "target", "_blank" );
		
		text=document.createTextNode(nodeName );//展示内容Title	//text=document.createTextNode(uniqueId+nodeName );id+Title
		a.appendChild(text);
	}
	else
	{
		text=document.createTextNode( nodeName );
		cell.appendChild(text);
	}
	if(isManage==1){
	//add child
	a=document.createElement("A");
	a.setAttribute( "id","add"+uniqueId );
	a.setAttribute( "href","category_add.jsp?parentid="+uniqueId);
	a.setAttribute( "title","add category");
	cell.appendChild( a );
	text=document.createTextNode("+");
	//a.appendChild(text);
	AddImage( a,"images/icon/add.gif");
	//remove child
	a=document.createElement("A");
	a.setAttribute( "id","del"+uniqueId );
	a.setAttribute( "href","category_del.jsp?id="+uniqueId);
	a.setAttribute( "title","remove category");
	cell.appendChild( a );
	text=document.createTextNode(" -");
	a.onclick =ConfirmDel;
	//a.appendChild(text);
	AddImage( a,"images/icon/del.gif");
	//preview
	/*a=document.createElement("A");
	a.setAttribute( "id","view"+uniqueId );
	a.setAttribute( "href","category_view.jsp?id="+uniqueId);
	a.setAttribute( "title","preview");
	a.setAttribute( "target","_blank");
	cell.appendChild( a );
	text=document.createTextNode(" -");
	AddImage( a,"images/icon/file.gif");*/
	}
	if(isManage==2){
	//add child
	a=document.createElement("A");
	a.setAttribute( "id","add"+uniqueId );
	a.setAttribute( "href","category_add.jsp?parentid="+uniqueId);
	a.setAttribute( "title","add category");
	cell.appendChild( a );
	text=document.createTextNode("+");
	//a.appendChild(text);
	AddImage( a,"images/icon/add.gif");
	}
	row.appendChild(cell);

	return CreateDiv( parent, uniqueId );;
}

function CreateDiv( parent, id )
{
	div=document.createElement("DIV");
	if( parent != null )
		parent.appendChild( div );
		
	div.setAttribute( "id", "D"+id );
	if(id==0){
		div.style.display  = "block";
	}
	else{
		div.style.display  = "none";
	}	
 	div.style.marginLeft = "2em";
	
	return div;
}


var rootCell = null;


function Initialise()
{
	body = document.getElementsByTagName("body").item(0);
	body.setAttribute( "leftmargin", 2 );
	body.setAttribute( "topmargin", 0 );
	body.setAttribute( "marginwidth", 0 );
	body.setAttribute( "marginheight", 0 );
	
	table = document.createElement("TABLE");
	body.appendChild( table );

	table.setAttribute( "border", 0 );
	table.setAttribute( "cellpadding", 1 );
	table.setAttribute( "cellspacing", 1 );
	table.setAttribute( "width","98%");
	table.setAttribute( "align","center");	
	table.style.border="1px solid #CCCCCC";
		
	tablebody = document.createElement("TBODY");
	table.appendChild(tablebody);
		
	row=document.createElement("TR");
	tablebody.appendChild(row);
		
	cell=document.createElement("TD");
	row.appendChild(cell); 	
	
	rootCell = cell;
}
