package edu.core.java.auction;


import java.io.*;
import java.text.ParseException;

import edu.core.java.auction.domain.*;
import edu.core.java.auction.repository.*;
import edu.core.java.auction.vo.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static ObjectMapper mapper = new ObjectMapper();
    private static AuctionService service = AuctionService.getInstance();
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    private static PropertiesProvider propertiesProvider = PropertiesProvider.getInstance();

    public static void main(String[] args) {
        logger.info("Application has started.");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("----------------------------------------------------");
            System.out.println(propertiesProvider.getApplicationProperties().getProperty("Add"));
            System.out.println(propertiesProvider.getApplicationProperties().getProperty("Delete"));
            System.out.println(propertiesProvider.getApplicationProperties().getProperty("Update"));
            System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_one"));
            System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_all"));
            System.out.println(propertiesProvider.getApplicationProperties().getProperty("Exit"));
            System.out.println(propertiesProvider.getApplicationProperties().getProperty("Separator"));
            int choice;
            try {
                choice = Integer.parseInt(reader.readLine());
                switch(choice) {
                    case 1 :
                        addExistedEntity();
                        break;
                    case 2 :
                        deleteEntity();
                        break;
                    case 3 :
                        updateEntity();
                        break;
                    case 4 :
                        showEntity();
                        break;
                    case 5 :
                        showEntities();
                        break;
                    case 6 :
                        logger.info("Application has stopped.");
                        return;
                    default:
                        logger.warn("Incorrect choice");
                }
            } catch (NumberFormatException | ParseException | IOException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    public static void showEntities() throws IOException, ParseException, NumberFormatException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_products"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_buyers"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_sellers"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_lots"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_bids"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Main_menu"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Separator"));
        int choice = Integer.parseInt(reader.readLine());
        switch(choice){
            case 1:
                for(Product product : service.getAllProducts()){
                    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(product));
                }
                break;
            case 2:
                for(Buyer buyer : service.getAllBuyers()){
                    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(buyer));
                }
                break;
            case 3:
                for(Seller seller : service.getAllSellers()){
                    System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(seller));
                }
                break;
            case 4:
                for(Lot lot : service.getAllLots()){
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lot));
                }
                break;
            case 5:
                for(Bid bid : service.getAllBids()){
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bid));
                }
                break;
            default:
                logger.warn("Incorrect choice.");
        }
    }

    public static void showEntity() throws IOException, ParseException, NumberFormatException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Enter_entity_id"));
        Long id = Long.parseLong(reader.readLine());
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_product"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_buyer"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_seller"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_lot"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Show_bid"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Main_menu"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Separator"));
        int choice2 = Integer.parseInt(reader.readLine());
        switch(choice2){
            case 1 :
                Product product = service.getProductById(id);
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(product));
                System.out.println();
                break;
            case 2 :
                Buyer buyer = service.getBuyerById(id);
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(buyer));
                break;
            case 3 :
                Seller seller = service.getSellerById(id);
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(seller));
                break;
            case 4 :
                Lot lot = service.getLotById(id);
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(lot));
                break;
            case 5 :
                Bid bid = service.getBidById(id);
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(bid));
                break;
            case 6 :
                break;
            default:
                logger.warn("Incorrect choice.");
        }
    }

    public static void deleteEntity() throws IOException, NumberFormatException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Delete_product"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Delete_buyer"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Delete_seller"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Delete_lot"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Delete_bid"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Main_menu"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Separator"));
        int choice = Integer.parseInt(reader.readLine());
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Enter_entity_id"));
        Long id = Long.parseLong(reader.readLine());
        switch(choice){
            case 1 :
                service.deleteProductById(id);
                logger.info("Product with id = " + id + " was deleted.");
                break;
            case 2 :
                service.deleteBuyerById(id);
                logger.info("Buyer with id = " + id + " was deleted.");
                break;
            case 3 :
                service.deleteSellerById(id);
                logger.info("Seller with id = " + id + " was deleted.");
                break;
            case 4 :
                service.deleteLotById(id);
                logger.info("LotValueObject with id = " + id + " was deleted.");
                break;
            case 5 :
                service.deleteBidById(id);
                logger.info("BidValueObject with id = " + id + " was deleted.");
                break;
            case 6 :
                break;
            default:
                logger.warn("Incorrect choice.");
        }
    }

    public static void updateEntity() throws IOException, ParseException, NumberFormatException{
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Enter_filepath"));
        String filePath = reader.readLine();
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Update_product"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Update_buyer"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Update_seller"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Update_lot"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Update_bid"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Main_menu"));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Separator"));
        int choice = Integer.parseInt(reader.readLine());
        switch(choice) {
            case 1:
                ProductValueObject product = mapper.readValue(new FileInputStream(filePath), ProductValueObject.class);
                System.out.println(product);

                do {
                    System.out.print(propertiesProvider.getApplicationProperties().getProperty("Edit_title"));
                    System.out.print(propertiesProvider.getApplicationProperties().getProperty("Edit_description"));
                    System.out.print(propertiesProvider.getApplicationProperties().getProperty("Set_owner"));
                    System.out.print(propertiesProvider.getApplicationProperties().getProperty("Main_menu"));
                    choice = Integer.parseInt(reader.readLine());
                    switch (choice) {
                        case 1:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_title"));
                            product.title = reader.readLine();
                            service.updateProduct(product);
                            logger.info("Product with ID = " + product.id + " was updated.");
                            break;
                        case 2:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_description"));
                            product.description = reader.readLine();
                            service.updateProduct(product);
                            logger.info("Product with ID = " + product.id + " was updated.");
                            break;
                        case 3:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_owner_id"));
                            product.ownerId = Long.parseLong(reader.readLine());
                            service.updateProduct(product);
                            logger.info("Product with ID = " + product.id + " was updated.");
                            break;
                        case 4:
                            break;
                        default:
                            logger.warn("Incorrect choice.");
                    }
                } while (choice != 4);

                mapper.writeValue(new FileOutputStream(filePath), product);
                logger.info("File with product ID = " + product.id + " was rewrote.");
                break;
            case 2:
                BuyerValueObject buyer = mapper.readValue(new FileInputStream(filePath), BuyerValueObject.class);
                System.out.println(buyer);

                do {
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Edit_name"));
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Set_money_amount"));
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Main_menu"));
                    choice = Integer.parseInt(reader.readLine());
                    switch (choice) {
                        case 1:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_name"));
                            buyer.name = reader.readLine();
                            service.updateBuyer(buyer);
                            logger.info("Buyer with ID = " + buyer.id + " was updated.");
                            break;
                        case 2:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_money_amount"));
                            buyer.accountBalance += Double.parseDouble(reader.readLine());
                            service.updateBuyer(buyer);
                            logger.info("Buyer with ID = " + buyer.id + " was updated.");
                            break;
                        case 3:
                            break;
                        default:
                            logger.warn("Incorrect choice.");
                    }
                } while (choice != 3);

                mapper.writeValue(new FileOutputStream(filePath), buyer);
                logger.info("File with buyer ID = " + buyer.id + " was rewrote.");
                break;
            case 3:
                SellerValueObject seller = mapper.readValue(new FileInputStream(filePath), SellerValueObject.class);
                System.out.println(seller);

                do {
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Edit_name"));
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Set_money_amount"));
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Set_commission_percentage"));
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Main_menu"));
                    choice = Integer.parseInt(reader.readLine());
                    switch (choice) {
                        case 1:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_name"));
                            seller.name = reader.readLine();
                            service.updateSeller(seller);
                            logger.info("Seller with ID = " + seller.id + " was updated.");
                            break;
                        case 2:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_money_amount"));
                            seller.accountBalance = Double.parseDouble(reader.readLine());
                            service.updateSeller(seller);
                            logger.info("Seller with ID = " + seller.id + " was updated.");
                            break;
                        case 3:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_commission_percentage"));
                            seller.commissionPercentage = Double.parseDouble(reader.readLine());
                            service.updateSeller(seller);
                            logger.info("Seller with ID = " + seller.id + " was updated.");
                            break;
                        case 4:
                            break;
                        default:
                            logger.warn("Incorrect choice.");
                    }
                } while (choice != 4);

                mapper.writeValue(new FileOutputStream(filePath), seller);
                logger.info("File with seller ID = " + seller.id + " was rewrote.");
                break;
            case 4:
                LotValueObject lot = mapper.readValue(new FileInputStream(filePath), LotValueObject.class);
                System.out.println(lot);

                do {
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Edit_end_date"));
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Replace_product_id"));
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Main_menu"));
                    choice = Integer.parseInt(reader.readLine());
                    switch (choice) {
                        case 1:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_end_date"));
                            String newEndDate = reader.readLine();
                            lot.endDate = service.getDateFormat().parse(newEndDate);
                            service.updateLot(lot);
                            logger.info("Lot with ID = " + lot.id + " was updated.");
                            break;
                        case 2:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_product_id"));
                            lot.productId = Long.parseLong(reader.readLine());
                            service.updateLot(lot);
                            logger.info("Lot with ID = " + lot.id + " was updated.");
                            break;
                        case 3:
                            break;
                        default:
                            logger.warn("Incorrect choice.");
                    }
                } while(choice != 3);

                mapper.writeValue(new FileOutputStream(filePath), lot);
                logger.info("File with lot ID = " + lot.id + " was rewrote.");
                break;
            case 5:
                BidValueObject bid = mapper.readValue(new FileInputStream(filePath), BidValueObject.class);
                System.out.println(bid);

                do{
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Edit_bid_amount"));
                    System.out.println(propertiesProvider.getApplicationProperties().getProperty("Main_menu"));
                    choice = Integer.parseInt(reader.readLine());
                    switch(choice){
                        case 1:
                            System.out.print(propertiesProvider.getApplicationProperties().getProperty("Enter_new_bid_amount"));
                            bid.amount = Double.parseDouble(reader.readLine());
                            service.updateBid(bid);
                            logger.info("Bid with ID = " + bid.id + " was updated.");
                            break;
                        case 2:
                            break;
                        default:
                            logger.warn("Incorrect choice.");
                    }
                } while (choice != 2);

                mapper.writeValue(new FileOutputStream(filePath), bid);
                logger.info("File with bid ID = " + bid.id + " was rewrote.");
                break;
            case 6:
                break;
        }
    }
    private static void addExistedEntity() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Enter_filepath"));
        String filePath = reader.readLine();
        System.out.println(propertiesProvider.getApplicationProperties().getProperty("Enter_type_of_entity"));
        int choice = Integer.parseInt(reader.readLine());
        switch (choice) {
            case 1:
                ProductValueObject productValueObject =
                        mapper.readValue(new FileInputStream(filePath), ProductValueObject.class);
                ProductRepository productRepository = service.getProductRepository();
                if (productRepository.contains(productValueObject.id)){
                    logger.warn("Product with id = " + productValueObject.id + " already exists.");
                    return;
                }
                productRepository.add(productValueObject);
                productRepository.incMaxId();
                logger.info("Product with id = " + productValueObject.id + " was added to repository.");
                break;
            case 2:
                BuyerValueObject buyerValueObject =
                        mapper.readValue(new FileInputStream(filePath), BuyerValueObject.class);
                BuyerRepository buyerRepository = service.getBuyerRepository();
                if (buyerRepository.contains(buyerValueObject.id)){
                    logger.warn("Buyer with id = " + buyerValueObject.id + " already exists.");
                    return;
                }
                buyerRepository.add(buyerValueObject);
                buyerRepository.incMaxId();
                logger.info("Buyer with id = " + buyerValueObject.id + " was added to repository.");
                break;
            case 3:
                SellerValueObject sellerValueObject =
                        mapper.readValue(new FileInputStream(filePath), SellerValueObject.class);
                SellerRepository sellerRepository = service.getSellerRepository();
                if (sellerRepository.contains(sellerValueObject.id)){
                    logger.warn("Seller with id = " + sellerValueObject.id + " already exists.");
                    return;
                }
                sellerRepository.add(sellerValueObject);
                sellerRepository.incMaxId();
                logger.info("Seller with id = " + sellerValueObject.id + " was added to repository.");
                break;
            case 4:
                LotValueObject lotValueObject =
                        mapper.readValue(new FileInputStream(filePath), LotValueObject.class);
                LotRepository lotRepository = service.getLotRepository();
                if (lotRepository.contains(lotValueObject.id)){
                    logger.warn("Lot with id = " + lotValueObject.id + " already exists.");
                    return;
                }
                lotRepository.add(lotValueObject);
                lotRepository.incMaxId();
                logger.info("Lot with id = " + lotValueObject.id + " was added to repository.");
                break;
            case 5:
                BidValueObject bidValueObject =
                        mapper.readValue(new FileInputStream(filePath), BidValueObject.class);
                BidRepository bidRepository = service.getBidRepository();
                if (bidRepository.contains(bidValueObject.id)){
                    logger.warn("Lot with id = " + bidValueObject.id + " already exists.");
                    return;
                }
                service.createBid(bidValueObject.id, bidValueObject.buyerId, bidValueObject.lotId, bidValueObject.amount);
                logger.info("Bid with id = " + bidValueObject.id + " was added to repository.");
                break;
            default:
                logger.warn("Incorrect choice.");
        }
    }
}
