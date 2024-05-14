package com.example.todaysbook.controller;

import com.example.todaysbook.domain.dto.CustomUserDetails;
import com.example.todaysbook.domain.dto.PaymentAddressAndMileageInfo;
import com.example.todaysbook.domain.dto.PaymentBookInfoDto;
import com.example.todaysbook.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class PaymentController {

    @Value("${tosspayment.client_key}")
    String widgetClientKey;
    @Value("${tosspayment.secret_key}")
    String widgetSecretKey;

    private final PaymentService paymentService;

    public static int getTotalPrice(List<PaymentBookInfoDto> bookDtoList) {
        int totalPrice = 0;
        for (PaymentBookInfoDto paymentBookInfoDto : bookDtoList) {
            totalPrice += paymentBookInfoDto.getPrice();
        }
        return totalPrice;
    }

//    private static int getTotalMileage(List<PaymentBookInfoDto> bookDtoList) {
//        int totalMileage = 0;
//        for (PaymentBookInfoDto paymentBookInfoDto : bookDtoList) {
//            totalMileage += paymentBookInfoDto.getMileage();
//        }
//        return totalMileage;
//    }
//    @GetMapping("/payment/virtual")
//    public String payWithVirtualAccount(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request, HttpServletResponse response, Model model) {
//        HttpSession session = request.getSession(false);
//
//        if (session == null) {
//            return "redirect:/";
//        }
//
//        long userId = userDetails.getUserId();
//        List<PaymentBookInfoDto> bookDtoList = (List<PaymentBookInfoDto>)session.getAttribute(String.valueOf(userId)+"_1");
//        PaymentAddressAndMileageInfo addressAndMileageInfo = (PaymentAddressAndMileageInfo) session.getAttribute(String.valueOf(userId) + "_2");
//        String orderName = "";
//
//        for (PaymentBookInfoDto book : bookDtoList) {
//            orderName += (book.getBookName() + " (" + book.getQuantity() + "권)\n");
//        }
//        orderName = orderName.substring(0, orderName.length()-1);
//        int totalPrice = getTotalPrice(bookDtoList);
//        int deliveryCharge = totalPrice >= 20000 ? 0 : 3000;
//        model.addAttribute("totalPrice", totalPrice-addressAndMileageInfo.getUsedMileage() + deliveryCharge);
//        model.addAttribute("orderName", orderName);
//        model.addAttribute("clientKey", widgetClientKey);
//
//        return "payment/pay_virtual";
//    }

    @GetMapping("/payment/card")
    public String payWithCreditCard(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request, HttpServletResponse response, Model model) {
        HttpSession session = request.getSession(false);

        if (session == null) {
            return "redirect:/";
        }

        long userId = userDetails.getUserId();
        List<PaymentBookInfoDto> bookDtoList = (List<PaymentBookInfoDto>)session.getAttribute(String.valueOf(userId)+"_1");
        PaymentAddressAndMileageInfo addressAndMileageInfo = (PaymentAddressAndMileageInfo) session.getAttribute(String.valueOf(userId) + "_2");
        String orderName = "";

        for (PaymentBookInfoDto book : bookDtoList) {
            orderName += (book.getBookName().length() > 10 ? book.getBookName().substring(0, 9) + "..." : book.getBookName()) + " (" + book.getQuantity() + "권)\n";
        }
        orderName = orderName.substring(0, orderName.length()-1);
        if (orderName.length() > 100) {
            orderName = orderName.substring(0, 89) + "...(이하 생략)";
        }

        int totalPrice = getTotalPrice(bookDtoList);
        int deliveryCharge = totalPrice >= 20000 ? 0 : 3000;
        model.addAttribute("totalPrice", totalPrice - addressAndMileageInfo.getUsedMileage() + deliveryCharge);
        model.addAttribute("orderName", orderName);
        model.addAttribute("clientKey", widgetClientKey);

        return "payment/pay_card";
    }

//    @PostMapping("/payment/virtual")
//    public ResponseEntity<String> payWithVirtualAccountPost(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody PaymentAddressAndMileageInfo addressAndMileageInfo, HttpServletRequest req, Model model) {
//        HttpSession session = req.getSession(true);
//        long userId = userDetails.getUserId();
//        session.setAttribute(String.valueOf(userId)+"_2", addressAndMileageInfo);
//        return ResponseEntity.ok("/payment/virtual");
//    }
    @PostMapping("/payment/card")
    public ResponseEntity<String> payWithCreditCardPost(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody PaymentAddressAndMileageInfo addressAndMileageInfo, HttpServletRequest req, Model model) {
        HttpSession session = req.getSession(true);
        long userId = userDetails.getUserId();
        session.setAttribute(String.valueOf(userId)+"_2", addressAndMileageInfo);
        return ResponseEntity.ok("/payment/card");
    }

    @RequestMapping(value = "/payment/success", method = RequestMethod.GET)
    public String paymentRequest(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request, Model model) throws Exception {
        HttpSession session = request.getSession(false);
        long userId = userDetails.getUserId();
        List<PaymentBookInfoDto> bookDtoList = (List<PaymentBookInfoDto>)session.getAttribute(String.valueOf(userId)+"_1");
        PaymentAddressAndMileageInfo addressAndMileageInfo = (PaymentAddressAndMileageInfo) session.getAttribute(String.valueOf(userId) + "_2");

        int totalPrice = PaymentController.getTotalPrice(bookDtoList);
        model.addAttribute("bookDtoList", bookDtoList);
        model.addAttribute("usedMileage", addressAndMileageInfo.getUsedMileage());
        model.addAttribute("totalPrice", totalPrice);
        long deliveryCharge = addressAndMileageInfo.getTotalPrice() >= 20000 ? 0 : 3000;
        model.addAttribute("mileagedTotalPrice", totalPrice - addressAndMileageInfo.getUsedMileage() + deliveryCharge);
        model.addAttribute("deliveryCharge", addressAndMileageInfo.getTotalPrice() >= 20000 ? "0원" : "3,000원");
        model.addAttribute("etc", "[" + addressAndMileageInfo.getUser() + "]님을 받는 분으로 하고, (" + addressAndMileageInfo.getPostcode() +
                ")[" + addressAndMileageInfo.getAddress() + " / " + addressAndMileageInfo.getDetailAddress() + "]을(를) 배송지로 합니다");
        return "payment/success";
    }

    @RequestMapping(value = "/payment/fail", method = RequestMethod.GET)
    public String failPayment(HttpServletRequest request, Model model) throws Exception {
        String failCode = request.getParameter("code");
        String failMessage = request.getParameter("message");

        model.addAttribute("code", failCode);
        model.addAttribute("message", failMessage);

        return "payment/fail";
    }

    @RequestMapping(value = "/payment/confirm")
    public ResponseEntity<JSONObject> confirmPayment(@RequestBody String jsonBody) throws Exception {

        JSONParser parser = new JSONParser();
        String orderId;
        String amount;
        String paymentKey;
        try {
            // 클라이언트에서 받은 JSON 요청 바디입니다.
            JSONObject requestData = (JSONObject) parser.parse(jsonBody);
            paymentKey = (String) requestData.get("paymentKey");
            orderId = (String) requestData.get("orderId");
            amount = (String) requestData.get("amount");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        };
        JSONObject obj = new JSONObject();
        obj.put("orderId", orderId);
        obj.put("amount", amount);
        obj.put("paymentKey", paymentKey);

        // TODO: 개발자센터에 로그인해서 내 결제위젯 연동 키 > 시크릿 키를 입력하세요. 시크릿 키는 외부에 공개되면 안돼요.
        // @docs https://docs.tosspayments.com/reference/using-api/api-keys

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        // @docs https://docs.tosspayments.com/reference/using-api/authorization#%EC%9D%B8%EC%A6%9D
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        // 결제 승인 API를 호출하세요.
        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        // @docs https://docs.tosspayments.com/guides/payment-widget/integration#3-결제-승인하기
        URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", authorizations);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);


        OutputStream outputStream = connection.getOutputStream();
        outputStream.write(obj.toString().getBytes("UTF-8"));

        int code = connection.getResponseCode();
        boolean isSuccess = code == 200;

        InputStream responseStream = isSuccess ? connection.getInputStream() : connection.getErrorStream();

        // TODO: 결제 성공 및 실패 비즈니스 로직을 구현하세요.
        Reader reader = new InputStreamReader(responseStream, StandardCharsets.UTF_8);
        // 주어진 reader를 읽어서 JSON 형식으로 변환한 후, 그 결과를 JSONObject로 반환합니다.
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        responseStream.close();

        return ResponseEntity.status(code).body(jsonObject);
    }

    @GetMapping("/payment/info")
    public String paymentInfo(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest req, Model model) {
        // userId가 1인 사용자의 장바구니 목록 조회
//        List<CartBook> cartBooks = cartService.findCartBooksByUserId(1L);
//        int totalPrice = cartService.calculateTotalPrice(cartBooks); // 총 상품 가격을 계산
        long userId = userDetails.getUserId();
        HttpSession session = req.getSession(false);
        List<PaymentBookInfoDto> bookDtoList = (List<PaymentBookInfoDto>)session.getAttribute(userDetails.getUserId()+"_1");

        model.addAttribute("totalPrice", PaymentController.getTotalPrice(bookDtoList)); // 모델에 totalPrice를 추가하여 뷰로 전달
        model.addAttribute("mileage",userDetails.getMileage()); // 모델에 totalPrice를 추가하여 뷰로 전달
        model.addAttribute("cartBooks", bookDtoList);

        return "payment/info";
    }

    @PostMapping("/payment/info")
    public ResponseEntity<String> paymentInfo(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request, @RequestBody List<PaymentBookInfoDto> books) throws Exception {
        HttpSession session = request.getSession(true);
        session.setAttribute(String.valueOf(userDetails.getUserId())+"_1", books);
        return ResponseEntity.ok("/payment/info");
    }


    @PostMapping("/payment/card/order")
    public ResponseEntity<String> makeOrder(@AuthenticationPrincipal CustomUserDetails userDetails, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession(false);
        long userId = userDetails.getUserId();
        List<PaymentBookInfoDto> bookDtoList = (List<PaymentBookInfoDto>)session.getAttribute(String.valueOf(userId)+"_1");
        PaymentAddressAndMileageInfo addressAndMileageInfo = (PaymentAddressAndMileageInfo) session.getAttribute(String.valueOf(userId) + "_2");

        paymentService.createOrder(userId, bookDtoList, addressAndMileageInfo);
        paymentService.subtractStock(bookDtoList);

        session.removeAttribute(userId+"_1");
        session.removeAttribute(userId+"_2");

        return ResponseEntity.ok("");
    }

    @PostMapping("/payment/webhook")
    public String webhook() {
        System.out.println("webhook");
        return "redirect:/";
    }
}