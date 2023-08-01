package com.example.finalproject.controller;

public class ConvertToPDF {
//    @RequestMapping(path = "/pdf")
//    public ResponseEntity<?> getPDF(HttpServletRequest request, HttpServletResponse response) throws IOException {
//
//        /* Do Business Logic*/
//
//        Order order = OrderHelper.getOrder();
//
//        /* Create HTML using Thymeleaf template Engine */
//
//        WebContext context = new WebContext(request, response, servletContext);
//        context.setVariable("orderEntry", order);
//        String orderHtml = templateEngine.process("order", context);
//
//        /* Setup Source and target I/O streams */
//
//        ByteArrayOutputStream target = new ByteArrayOutputStream();
//
//        /*Setup converter properties. */
//        ConverterProperties converterProperties = new ConverterProperties();
//        converterProperties.setBaseUri("http://localhost:8080");
//
//        /* Call convert method */
//        HtmlConverter.convertToPdf(orderHtml, target, converterProperties);
//
//        /* extract output as bytes */
//        byte[] bytes = target.toByteArray();
//
//
//        /* Send the response as downloadable PDF */
//
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_PDF)
//                .body(bytes);
//
//    }
}
