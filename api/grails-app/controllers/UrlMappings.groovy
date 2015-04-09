class UrlMappings {

    static mappings = {

        group("/carts") {
            "/"(controller: 'cart', action: ['GET': 'list', 'POST': 'create'])
            "/$cartId"(controller: 'cart', action: ['GET': 'read', 'DELETE': 'delete'])
        }

        group("/carts/$cartId/item") {
            "/"(controller: 'cart', action: ['GET': 'itemList', 'POST': 'itemAdd'])
            "/$cartItemId"(controller: 'cart', action: ['PUT': 'itemUpdate', 'DELETE': 'itemDelete'])
        }

        group("/items") {
            "/"(controller: 'item', action: ['GET': 'list', 'POST': 'create'])
        }

        "/"(view:"/index")
        "500"(view:'/error')
        "404"(view:'/notFound')
    }
}
