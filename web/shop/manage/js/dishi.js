   function getXMLRequester( )
                            {
                                var xmlhttp_request = false;
                                try {
                                    if (window.ActiveXObject)
                                    {
                                       
                                        for (var i = 5; i; i--) {
                                            try {
                                                if (i == 2)
                                                {
                                                    xmlhttp_request = new ActiveXObject("Microsoft.XMLHTTP");
                                                }
                                                else
                                                {
                                                    xmlhttp_request = new ActiveXObject("Msxml2.XMLHTTP." + i + ".0");
                                                    xmlhttp_request.setRequestHeader("Content-Type", "text/xml");
                                                    xmlhttp_request.setRequestHeader("Content-Type", "gb2312");
                                                }
                                                break;
                                            }
                                            catch (e) {
                                                xmlhttp_request = false;
                                            }
                                        }
                                    }
                                    else if (window.XMLHttpRequest)
                                    {
                                        xmlhttp_request = new XMLHttpRequest();
                                        if (xmlhttp_request.overrideMimeType)
                                        {
                                            xmlhttp_request.overrideMimeType('text/xml');
                                        }
                                    }
                                }
                                catch (e) {
                                    xmlhttp_request = false;
                                }
                                return xmlhttp_request;
                            }

                            function GetEduArea(areaid, eduareaid, jilian) {
                                //var id=document.getElementById();
                                var areaSelect = document.getElementById(jilian);
                                // alert(jilian);
                                areaSelect.options.length = 0;
                                var xmlhttp;
                                xmlhttp = getXMLRequester();
                                if (xmlhttp) {
                                    xmlhttp.onreadystatechange = function doAction() {
                                        if (xmlhttp.readyState == 4)
                                        {
                                            /*alert(xmlhttp.responseText);*/
                                            var response = xmlhttp.responseXML.documentElement;
                                            var classidList = response.getElementsByTagName('area');
                                            areaSelect.options[0] = new Option("选择", 0);
                                            for (var i = 0; i < classidList.length; i++) {
                                                xx = classidList[i].getElementsByTagName('id');
                                                yy = classidList[i].getElementsByTagName('title');
                                                classid = xx[0].firstChild.data;
                                                classtitle = yy[0].firstChild.data;
                                                //alert( classid);
                                                areaSelect.options[i + 1] = new Option(classtitle, classid);
                                                if (eduareaid == classid) {
                                                    areaSelect.options[i + 1].selected = true;
                                                }
                                            }
                                        }
                                    };
                                    xmlhttp.open("POST", "/test.jsp?id=" + areaid + "&jilian=" + jilian, true);
                                    xmlhttp.send("");
                                }
                            }