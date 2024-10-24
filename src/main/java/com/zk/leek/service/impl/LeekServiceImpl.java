package com.zk.leek.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zk.leek.mapper.StockIndicatorsMapper;
import com.zk.leek.mapper.StockMapper;
import com.zk.leek.model.Stock;
import com.zk.leek.model.StockIndicators;
import com.zk.leek.service.LeekService;
import com.zk.leek.util.DateUtil;
import com.zk.leek.vo.IndicatorsDesc;
import com.zk.leek.vo.StockVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Service
public class LeekServiceImpl implements LeekService {

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private StockIndicatorsMapper stockIndicatorsMapper;

    @Autowired
    private RestTemplate restTemplate;

    private ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(200, 5000, 60L, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5000));

    String stockUrl = "https://stock.xueqiu.com/v5/stock/screener/quote/list.json?page=%s&size=90&order=desc&order_by=percent&market=CN&type=sh_sz";
    String indicatorsUrl = "https://finance.pae.baidu.com/vapi/v1/getquotation?all=1&srcid=5353&pointType=string&group=quotation_minute_ab&market_type=ab&new_Format=1&finClientType=pc&query=%s&code=%s";

    @Override
    public void init() {
//        String filePath = "C:\\Users\\MXC\\AppData\\Local\\Temp\\Rar$DIa8380.38188\\new 7"; // 替换为你的大文件路径
//        StringBuilder contentBuilder = new StringBuilder();
//        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
//            stream.forEach(contentBuilder::append);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // 现在contentBuilder包含了文件的全部内容
//        String content = contentBuilder.toString();
        for (int i = 1 ; i <= 56; i++){
            String codeUrl = String.format(stockUrl, i);
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");
            headers.set("cookie", "PSTM=1705474285; BIDUPSID=1CA44D8488709B8618BC1BE3299E0D41; MAWEBCUID=web_EHNxADXarZAhBOBezWvzSIOTOrdIGVSImfclmOLcmNJVXjarOC; BAIDUID=62693C7DE029306C6023B9A643B7AF87:FG=1; BDUSS=ZtOGpoVzlzUkhSb2pRSERHZmFufkVMZ0RiUDJLQXh-eTkySmcwNE1qNW1YSzltSVFBQUFBJCQAAAAAAAAAAAEAAACQhElAy6vM-M7otcTT4wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGbPh2Zmz4dmTW; BDUSS_BFESS=ZtOGpoVzlzUkhSb2pRSERHZmFufkVMZ0RiUDJLQXh-eTkySmcwNE1qNW1YSzltSVFBQUFBJCQAAAAAAAAAAAEAAACQhElAy6vM-M7otcTT4wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGbPh2Zmz4dmTW; H_WISE_SIDS=60852_60620_60883_60875; MCITY=-%3A; H_WISE_SIDS_BFESS=60852_60620_60883_60875; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=60852_60620_60883_60875; BDSFRCVID=WfkOJeC62959DMnJQGLHMZEBIg4O1S7TH6ao8Mza3x1eggbwgy4_EG0PCM8g0KuMszDGogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0x5; H_BDCLCKID_SF=JbAtVIPbf-5KD5rvK4QBq4tehHRhyhO9WDTm_DoTBK5Nb-Q_0tcMXMulQt5AK5Qqb6Tq-pPKKR7xVIOmhxjOMn0PhptjqTvX3mkjbT6Gfn02OPKzXUbf0-4syP4eKMRnWnnRKfA-b4ncjRcTehoM3xI8LNj405OTbIFO0KJzJCFbhIK9e5-35JDjqxnH5RFXKKOLVbP5tp7keq8CDxJcjfob546I2R3hBGQ45qTjJ4QhftQ2y5jHhp09DpOX0tnWbg5yLKtyfnrpsIJMqq_WbT8U5ec93-3TaKviaKOjBMb1MhbDBT5h2M4qMxtOLR3pWDTm_q5TtUJMeCnTDMFhe6JyeHAjt58jf5vfL5rt-Jj_e-nG-tI_h4L3enJhaMRZ5mAqoDIaflbYDlchQUv1XqIkL4rMtU4DLbvnaIQqaKnYJRDR25QqbP-V5UJ8tpR43bRT5hCy5KJvfq6R2xOnhP-UyNbMWh37JgnlMKoaMp78jR093JO4y4Ldj4oxJpOJ5JbMoPPafJOKHIC4j682jx5; BAIDUID_BFESS=62693C7DE029306C6023B9A643B7AF87:FG=1; BDSFRCVID_BFESS=WfkOJeC62959DMnJQGLHMZEBIg4O1S7TH6ao8Mza3x1eggbwgy4_EG0PCM8g0KuMszDGogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0x5; H_BDCLCKID_SF_BFESS=JbAtVIPbf-5KD5rvK4QBq4tehHRhyhO9WDTm_DoTBK5Nb-Q_0tcMXMulQt5AK5Qqb6Tq-pPKKR7xVIOmhxjOMn0PhptjqTvX3mkjbT6Gfn02OPKzXUbf0-4syP4eKMRnWnnRKfA-b4ncjRcTehoM3xI8LNj405OTbIFO0KJzJCFbhIK9e5-35JDjqxnH5RFXKKOLVbP5tp7keq8CDxJcjfob546I2R3hBGQ45qTjJ4QhftQ2y5jHhp09DpOX0tnWbg5yLKtyfnrpsIJMqq_WbT8U5ec93-3TaKviaKOjBMb1MhbDBT5h2M4qMxtOLR3pWDTm_q5TtUJMeCnTDMFhe6JyeHAjt58jf5vfL5rt-Jj_e-nG-tI_h4L3enJhaMRZ5mAqoDIaflbYDlchQUv1XqIkL4rMtU4DLbvnaIQqaKnYJRDR25QqbP-V5UJ8tpR43bRT5hCy5KJvfq6R2xOnhP-UyNbMWh37JgnlMKoaMp78jR093JO4y4Ldj4oxJpOJ5JbMoPPafJOKHIC4j682jx5; delPer=0; PSINO=6; BA_HECTOR=8la4208h008g25012h800581bvdkd51jhegj41u; ZFY=THCbUZ7VzOeZy43:Bhjhg3XqtxHKe0bQxcVI7asoW1D4:C; RT=\"z=1&dm=baidu.com&si=09c7257d-eef2-4ff7-a6c7-57153c68fd7a&ss=m2k415uf&sl=4&tt=5g0&bcn=https%3A%2F%2Ffclog.baidu.com%2Flog%2Fweirwood%3Ftype%3Dperf&ld=29nx\"; ab_sr=1.0.1_YTFlNTVhNWFlMTA4MzFkZjRmZWExNWY1MDQxYzFhOWZmZTkxY2UyYTk5ZmU0MjNmZjExODBiYTAxZTUwOWZhMGVlYzVlNjI1NTA0ZDE2NGU2MDNjYmJmYTRlM2RmZjZhMzBiYjgwNjA1Yzc3YTdlNTIzYjdhOGYwY2MwZjkxYzBlZDEwYWE1NzlhZDZkNjViNmFiNGFiODUxOTk1ZGQwMA==");
            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> forEntity = restTemplate.exchange(codeUrl, HttpMethod.GET, entity, String.class);
            JSONArray jsonArray = JSON.parseObject(forEntity.getBody()).getJSONObject("data").getJSONArray("list");
            List<Stock> list = new ArrayList<>();
            for (int j = 0; j < jsonArray.size(); j++) {
                Stock stock = new Stock();
                JSONObject object = jsonArray.getJSONObject(j);
                String name = object.get("name").toString();
                String symbol = object.get("symbol").toString();
                stock.setName(name);
                stock.setSymbol(symbol);
                stock.setType(symbol.substring(0,2));
                stock.setCode(symbol.substring(2));
                list.add(stock);
            }
            stockMapper.batchInsert(list);
        }

//
//
//        List<Stock> stockList = JSON.parseArray(content, Stock.class);
//        stockList.forEach(s -> s.setType("sz"));
//        stockMapper.batchInsert(stockList);
    }

    @Override
    public void stat() {
        List<Stock> stockList = stockMapper.getAll();
        System.out.println(System.currentTimeMillis());
        getNetDateAndUpdate(stockList);
        System.out.println(System.currentTimeMillis());
    }

    private void getNetDateAndUpdate(List<Stock> stockList) {
        for (Stock stock : stockList) {
            threadPoolExecutor.execute(() -> {
                String codeUrl = String.format(indicatorsUrl, stock.getCode(), stock.getCode());
                HttpHeaders headers = new HttpHeaders();
                headers.set("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/129.0.0.0 Safari/537.36");
                headers.set("cookie", "PSTM=1705474285; BIDUPSID=1CA44D8488709B8618BC1BE3299E0D41; MAWEBCUID=web_EHNxADXarZAhBOBezWvzSIOTOrdIGVSImfclmOLcmNJVXjarOC; BAIDUID=62693C7DE029306C6023B9A643B7AF87:FG=1; BDUSS=ZtOGpoVzlzUkhSb2pRSERHZmFufkVMZ0RiUDJLQXh-eTkySmcwNE1qNW1YSzltSVFBQUFBJCQAAAAAAAAAAAEAAACQhElAy6vM-M7otcTT4wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGbPh2Zmz4dmTW; BDUSS_BFESS=ZtOGpoVzlzUkhSb2pRSERHZmFufkVMZ0RiUDJLQXh-eTkySmcwNE1qNW1YSzltSVFBQUFBJCQAAAAAAAAAAAEAAACQhElAy6vM-M7otcTT4wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGbPh2Zmz4dmTW; H_WISE_SIDS=60852_60620_60883_60875; MCITY=-%3A; H_WISE_SIDS_BFESS=60852_60620_60883_60875; BAIDUID_BFESS=62693C7DE029306C6023B9A643B7AF87:FG=1; delPer=0; PSINO=6; BA_HECTOR=01a42g8400ak258h8h0g24ag9hs0c71jhjpah1u; ZFY=THCbUZ7VzOeZy43:Bhjhg3XqtxHKe0bQxcVI7asoW1D4:C; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=60852_60620_60883_60875; BCLID=10289878509467506847; BCLID_BFESS=10289878509467506847; BDSFRCVID=NuDOJexroG3WSk7JySe9MZEBIFS16vJTDYrEOwXPsp3LGJLVdaSOEG0Pt_1MvLKh3uj4ogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; BDSFRCVID_BFESS=NuDOJexroG3WSk7JySe9MZEBIFS16vJTDYrEOwXPsp3LGJLVdaSOEG0Pt_1MvLKh3uj4ogKK3gOTH4DF_2uxOjjg8UtVJeC6EG0Ptf8g0M5; H_BDCLCKID_SF=tJIHVCPXJDD3H48k-4QEbbQH-UnLqbJmtgOZ04n-ah05o5C4jU6BX58YXJ643fcq5eoPXC5m3UTKsq76Wh35K5tTQP6rLqcry6Q4KKJxbPOYMDQtLP50XlO-hUJiB5JMBan7_pbIXKohJh7FM4tW3J0ZyxomtfQxtNRJ0DnjtpChbC_GD6uWej5LeU5eetjK2CntsJOOaCvoJhQOy4oWK441DnALJjbZMmJuhl6u3lozfRQ83Juh3M04K4o9-hvT-54e2p3FBUQZ8MTuQft20b0yXH_q3fJaMeoTVR7jWhk5ep72y5OUQlRX5q79atTMfNTJ-qcH0KQpsIJM5-DWbT8IjHCtt6tjfnFe_Cvt-5rDHJTg5DTjhPrMhPQiWMT-MTryKKO6LbkbO4TbbxoYWxLYytjgJ65qWHnRh4oNB-3iV-OxDUvnyxAZbMQgWMQxtNRJMKjzbqRvjKOGM6OobUPUDUJ9LUkJ3gcdot5yBbc8eIna5hjkbfJBQttjQn3hfIkj2CKLJCK5hKPCDT_3-RJH-xQ0KnLXKKOLVMccHl7ketn4hUt53ptfjP68Q-QKBGQ45qTILKD-flc2QhrKQf4WWb3ebTJr32Qr-JbqLh7psIJM5hPaXJKTyUAe-4IJaKviaKOjBMb1MhbDBT5h2M4qMxtOLR3pWDTm_q5TtUJMeCnTDMFhe6Q0DN0OtjttfKresJoq2RbhKROvhjRF5f0gyxoObtRxtavgBK3FMxOqbq6LQf5zQlJLjljZLU3k-eT9LMnx--t58h3_Xhjf-qtgQttjQn3dtmjq2pvSt-58Mb7TyU45bU47yaOR0q4Hb6b9BJcjfU5MSlcNLTjpQT8r5MDOK5OuJRQ2QJ8BJIIbMIjP; H_BDCLCKID_SF_BFESS=tJIHVCPXJDD3H48k-4QEbbQH-UnLqbJmtgOZ04n-ah05o5C4jU6BX58YXJ643fcq5eoPXC5m3UTKsq76Wh35K5tTQP6rLqcry6Q4KKJxbPOYMDQtLP50XlO-hUJiB5JMBan7_pbIXKohJh7FM4tW3J0ZyxomtfQxtNRJ0DnjtpChbC_GD6uWej5LeU5eetjK2CntsJOOaCvoJhQOy4oWK441DnALJjbZMmJuhl6u3lozfRQ83Juh3M04K4o9-hvT-54e2p3FBUQZ8MTuQft20b0yXH_q3fJaMeoTVR7jWhk5ep72y5OUQlRX5q79atTMfNTJ-qcH0KQpsIJM5-DWbT8IjHCtt6tjfnFe_Cvt-5rDHJTg5DTjhPrMhPQiWMT-MTryKKO6LbkbO4TbbxoYWxLYytjgJ65qWHnRh4oNB-3iV-OxDUvnyxAZbMQgWMQxtNRJMKjzbqRvjKOGM6OobUPUDUJ9LUkJ3gcdot5yBbc8eIna5hjkbfJBQttjQn3hfIkj2CKLJCK5hKPCDT_3-RJH-xQ0KnLXKKOLVMccHl7ketn4hUt53ptfjP68Q-QKBGQ45qTILKD-flc2QhrKQf4WWb3ebTJr32Qr-JbqLh7psIJM5hPaXJKTyUAe-4IJaKviaKOjBMb1MhbDBT5h2M4qMxtOLR3pWDTm_q5TtUJMeCnTDMFhe6Q0DN0OtjttfKresJoq2RbhKROvhjRF5f0gyxoObtRxtavgBK3FMxOqbq6LQf5zQlJLjljZLU3k-eT9LMnx--t58h3_Xhjf-qtgQttjQn3dtmjq2pvSt-58Mb7TyU45bU47yaOR0q4Hb6b9BJcjfU5MSlcNLTjpQT8r5MDOK5OuJRQ2QJ8BJIIbMIjP; ab_sr=1.0.1_NTA1NThkMjk4MWU5ODkxM2JhYTcxNjBjYWRmODIwYTRlYzk3NGM3MWY0YTUyZDAxN2ZmMzJiZjdkMjdlYjkxYzBhMzNjOWJhODEwNGMxM2MzYWQxNzkzNjJmYzQxMDQ4NzM2NjlkNmFhNmMwZWZhMGFkNWRiZjkwN2I4Zjg0YmU3MmY0MmNjZmZjN2ZiZjQ4MTcxNDk1YTlkZTAyNGRkZQ==");
                HttpEntity<String> entity = new HttpEntity<>(headers);
                ResponseEntity<String> forEntity = restTemplate.exchange(codeUrl, HttpMethod.GET, entity, String.class);
                JSONObject jsonObject = JSON.parseObject(forEntity.getBody());
                JSONArray jsonArray = jsonObject.getJSONObject("Result").getJSONObject("pankouinfos").getJSONArray("list");
                JSONObject cur = jsonObject.getJSONObject("Result").getJSONObject("cur");
                StockIndicators stockIndicators = new StockIndicators();
                stockIndicators.setAllData(jsonArray.toJSONString());
                stockIndicators.setStockCode(stock.getCode());
                stockIndicators.setStockId(stock.getId());
                stockIndicators.setStatDate(new Date());
                stockIndicators.setPrice(new BigDecimal(cur.get("price").toString()));
                stockIndicators.setRatiostr(cur.get("ratio").toString());
                stockIndicators.setRatio(new BigDecimal(cur.get("ratio").toString().substring(0, cur.get("ratio").toString().length() - 1)));
                stockIndicators.setIncrease(cur.get("increase").toString());
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    IndicatorsDesc desc = JSON.parseObject(object.toJSONString(), IndicatorsDesc.class);
                    String ename = desc.getEname();
                    setValue(stockIndicators, desc, ename);
                }
                stockIndicatorsMapper.delByDateAndCode(new Date(), stock.getCode());
                stockIndicatorsMapper.insert(stockIndicators);
                System.out.println(Thread.currentThread().getName() + " " + stock.getName());
            });
        }
    }

    @Override
    public List<Stock> getList() {
        List<Stock> stockList = stockMapper.getAll();
        List<Stock> result = new ArrayList<>();
        for (Stock stock : stockList) {
            List<StockIndicators> stockIndicatorsList = stockIndicatorsMapper.getByStockId(stock.getId());
            BigDecimal lastRatio = new BigDecimal(-100);
            boolean isA = true;
            for (StockIndicators stockIndicators : stockIndicatorsList){
               isA = isA && stockIndicators.getRatio().compareTo(lastRatio) >= 0;
                lastRatio = stockIndicators.getRatio();
            }
            if (isA){
                result.add(stock);
            }
        }
        return result;
    }

    @Override
    public StockVO getRandom() {
        List<Stock> all = stockMapper.getAll();
        return null;
    }

    @Override
    public void statOther() {
        List<Stock> stockList = stockMapper.getNotData(DateUtil.showFormatedDate( "yyyy-MM-dd", new Date()));
        System.out.println(System.currentTimeMillis());
        getNetDateAndUpdate(stockList);
        System.out.println(System.currentTimeMillis());
    }

    private void setValue(StockIndicators stockIndicators, IndicatorsDesc desc, String ename) {
        switch (ename){
            case "open":
                stockIndicators.setOpen(new BigDecimal(desc.getOriginValue()));
                break;
            case "high":
                stockIndicators.setHigh(new BigDecimal(desc.getOriginValue()));
                break;
            case "volume":
                stockIndicators.setVolume(Long.parseLong(desc.getOriginValue()));
                break;
            case "preClose":
                stockIndicators.setPreClose(new BigDecimal(desc.getOriginValue()));
                break;
            case "low":
                stockIndicators.setLow(new BigDecimal(desc.getOriginValue()));
                break;
            case "amount":
                stockIndicators.setAmount(new BigDecimal(desc.getOriginValue()));
                break;
            case "turnoverRatio":
                stockIndicators.setTurnoverRatio(new BigDecimal(desc.getOriginValue()));
                break;
            case "peratio":
                stockIndicators.setPeratio(new BigDecimal(desc.getOriginValue()));
                break;
            case "capitalization":
                stockIndicators.setCapitalization(new BigDecimal(desc.getOriginValue()));
                break;
            case "volumeRatio":
                stockIndicators.setVolumeRatio(new BigDecimal(desc.getOriginValue()));
                break;
            case "lyr":
                stockIndicators.setLyr(new BigDecimal(desc.getOriginValue()));
                break;
            case "totalShareCapital":
                stockIndicators.setTotalShareCapital(new BigDecimal(desc.getOriginValue()));
                break;
            case "weibiRatio":
                stockIndicators.setWeibiRatio(new BigDecimal(desc.getOriginValue()));
                break;
            case "avgPrice":
                stockIndicators.setAvgPrice(new BigDecimal(desc.getOriginValue()));
                break;
            case "currencyValue":
                stockIndicators.setCurrencyValue(new BigDecimal(desc.getOriginValue()));
                break;
            case "limitUp":
                stockIndicators.setLimitUp(new BigDecimal(desc.getOriginValue()));
                break;
            case "priceLimit":
                stockIndicators.setPriceLimit(new BigDecimal(desc.getOriginValue()));
                break;
            case "circulatingCapital":
                stockIndicators.setCirculatingCapital(new BigDecimal(desc.getOriginValue()));
                break;
            case "limitDown":
                stockIndicators.setLimitDown(new BigDecimal(desc.getOriginValue()));
                break;
            case "amplitudeRatio":
                stockIndicators.setAmplitudeRatio(new BigDecimal(desc.getOriginValue()));
                break;
            case "inside":
                stockIndicators.setInside(Long.parseLong(desc.getOriginValue()));
                break;
            case "pricesaleRatio":
//                        stockIndicators.setPricesaleRatio(new BigDecimal(desc.getOriginValue()));
                break;
            case "bvRatio":
                stockIndicators.setBvRatio(new BigDecimal(desc.getOriginValue()));
                break;
            case "outside":
                stockIndicators.setOutside(Long.parseLong(desc.getOriginValue()));
                break;
            case "w52_high":
                stockIndicators.setW52High(new BigDecimal(desc.getOriginValue()));
                break;
            case "w52_low":
                stockIndicators.setW52Low(new BigDecimal(desc.getOriginValue()));
                break;
            case "profit_flag":
                stockIndicators.setProfitFlag(desc.getOriginValue());
                break;
            case "vote_right_flag":
                stockIndicators.setVoteRightFlag(desc.getOriginValue());
                break;
            case "ptcc_flag":
                stockIndicators.setPtccFlag(desc.getOriginValue());
                break;
            case "reg_flag":
                stockIndicators.setRegFlag(desc.getOriginValue());
                break;
            default:
                // Handle unknown ename or ignore
                break;
        }
    }
}
