package pos.machine;


import com.sun.tools.javac.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PosMachine {

    public String printReceipt(List<String> barcodes) {
        return generateReceiptList(getBarcodeQuantity(barcodes));
    }

    public List<Pair<String, Integer>> getBarcodeQuantity(List<String> barcodes) {
        List<String> distinctBarcodes = barcodes.stream().distinct().collect(Collectors.toList());
        List<Pair<String, Integer>> distinctBarcodeWithQuantity = new ArrayList<>();

        for(String distinctBarcode: distinctBarcodes) {
            Integer countBarcode = 0;
            for(String barcode: barcodes) {
                if(distinctBarcode.equals(barcode)) {
                    countBarcode++;
                }
            }
            Pair<String, Integer> pair = new Pair<>(distinctBarcode, countBarcode);
            distinctBarcodeWithQuantity.add(pair);
        }

        return distinctBarcodeWithQuantity;
    }

    public String generateReceiptList(List<Pair<String, Integer>> barcodes) {

        String receiptList = "***<store earning no money>Receipt***\n";

        for(Pair<String, Integer> barcode: barcodes) {
            receiptList += generateReceiptLine(barcode);
        }

        receiptList += "----------------------\n" +
                "Total: 24 (yuan)\n" +
                "**********************";

        return receiptList;
    }

    public String generateReceiptLine(Pair<String, Integer> barcodeQuantityPair) {

        ItemInfo itemInfo = findItemBarCodeFromDB(barcodeQuantityPair.fst);

        return "Name: "+
                itemInfo.getName()+
                ", Quantity: "+
                barcodeQuantityPair.snd+
                ", Unit price: "+
                itemInfo.getPrice() +
                " (yuan), Subtotal: "+ calculateSubtotal(itemInfo, barcodeQuantityPair.snd) +
                " (yuan)\n";
    }

//    public ItemInfo findItemBarCodeFromDB(String barcode) {
//        List<ItemInfo> database = ItemDataLoader.loadAllItemInfos();
//
//        for(ItemInfo itemInfo : database) {
//            if(itemInfo.getBarcode().equals(barcode)) {
//                return itemInfo;
//            }
//        }
//
//        return null;
//    }
//
//    public int calculateSubtotal(ItemInfo item, int unit) {
//        return item.getPrice()*unit;
//    }




}
