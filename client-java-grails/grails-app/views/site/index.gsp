<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Welcome to Grails</title>
    <script src="https://unpkg.com/vue/dist/vue.min.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>
<body>

<div id="header">AGUANTE EL BACKEND</div>

<div id="sites">

<select id="select" onchange="sites.fetchCategories(this.value)">
    <option value="null">Selecionar</option>
    <g:each in="${sites}" var="site">
        <option value="${site?.id}">${site?.name}</option>
    </g:each>
</select>

    <select id="select_cat" onchange="sites.fetchCategoriesChildren(this.value)">
        <option :value=category.id v-for="category in categories">{{category.name}}</option>
    </select>

    <select id="select_child" onchange="sites.fetchCategoriesChildren(this.value)" >
        <option :value=child.id v-for="child in childrens">{{child.name}}</option>
    </select>

    <p id="demo"></p>

    <div id="imagen"></div>


</div>

<!-- Modal -->
<div id="mymodal" class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="titleModal">Titulo Modal</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <p id="idModal"></p>
                <div id="imgModal"></div>
                <p id="totalItemModal"></p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal" onclick="sites.deleteElement()" value="Click">Delete Element</button>
                <button type="button" class="btn btn-secondary" data-dismiss="modal" value="Click">Edit Element</button>
            </div>
        </div>
    </div>

    <button class="open-button" onclick="openForm()">Open Form</button>



</div>

<div id="footer">FIN DE LA PAGINA</div>

<script>
    var sites = new Vue({

        el:"#sites",
        data: {
            categories:[],
            childrens: [],
            pathSite: "",
            idshow: null
        },

        methods:{

            fetchCategories: function(strSite){
                sites.pathSite = ""
                axios.get('/site/categorias/',{
                    params:{
                        id:strSite
                    }
                }).then(function (response) {
                    console.log(response.data)
                    console.log(response.data.categories.children_categories)
                    sites.categories = response.data.categories.children_categories;
                    <%-- sites.categories = response.data.categories --%>
                }).catch(function (error) {
                    console.log(error)
                })
            },


            fetchCategoriesChildren: function(strSite){
                console.log(strSite)
                axios.get('/site/children/',{
                    params:{
                        id:strSite
                    }
                }).then(function (response) {
                    console.log(response.data)
                    sites.pathSite = sites.pathSite + " - " + response.data.childrens.name;
                    document.getElementById("demo").innerHTML = sites.pathSite;
                    if(response.data.childrens.children_categories.length == 0){
                        sites.idshow = response.data.childrens.id
                        if (response.data.childrens.picture != null) {
                            $("#mymodal").modal("show");
                            $("#titleModal").html(response.data.childrens.name)
                            $("#idModal").html("ID: " + response.data.childrens.id)
                            $("#totalItemModal").html("TOTAL DE ELEMENTOS: " + response.data.childrens.total_items_in_this_category)
                            $("#imgModal").html("<img src=" + response.data.childrens.picture + ">")

                        }
                    } else{
                        sites.childrens = response.data.childrens.children_categories;
                    }
                }).catch(function (error) {
                    console.log(error)
                })
            },

            deleteElement: function() {
                axios.get('/site/deleteElement/',{
                    params:{
                        articuloId: sites.idshow
                    }
                }).then(function () {
                    console.log("Elemento borrado")
                }).catch(function (error) {
                    console.log(error)
                })
            }
        }
    })

</script>

</body>



</html>
