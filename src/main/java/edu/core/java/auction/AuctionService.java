package edu.core.java.auction;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import edu.core.java.auction.domain.*;
import edu.core.java.auction.loader.*;
import edu.core.java.auction.repository.*;
import edu.core.java.auction.repository.collection.*;
import edu.core.java.auction.repository.database.*;
import edu.core.java.auction.translator.*;
import edu.core.java.auction.vo.*;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Maxim on 18.03.2017.
 */
public class AuctionService {
    private static AuctionService instance = new AuctionService();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private HashMap<Long, Timer> timers = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(AuctionService.class);

    private class LotHandler extends TimerTask{
        private Long lotId;

        public LotHandler(Long lotId){
            this.lotId = lotId;
        }

        @Override
        public void run() {
//            LotValueObject lotValueObject = lotRepository.find(lotId);
//            ProductValueObject productValueObject = productRepository.find(lotValueObject.productId);
//
//            System.out.println("Auction has ended. Product name: " + productValueObject.title);
//            if (lotValueObject.currentBidId != 0){
//                BidValueObject bidValueObject = bidRepository.find(lotValueObject.currentBidId);
//                BuyerValueObject winner = buyerRepository.find(bidValueObject.buyerId);
//
//                SellerValueObject sellerValueObject = sellerRepository.find(productValueObject.ownerId);
//                sellerValueObject.accountBalance += bidValueObject.amount * (100 - sellerValueObject.commissionPercentage)/100;
//                sellerRepository.update(sellerValueObject);
//                productRepository.delete(productValueObject.id);
//                bidRepository.delete(lotValueObject.currentBidId);
//                System.out.println("The winner is " + winner.name);
//            } else {
//                System.out.println("No winner.");
//            }
//
//            lotRepository.delete(lotId);
//            timers.remove(lotId);
        }
    }

    // Repositories

    private BidRepository bidRepository;
    private LotRepository lotRepository;
    private BuyerRepository buyerRepository;
    private SellerRepository sellerRepository;
    private ProductRepository productRepository;

    // Translators

    private BidTranslator bidTranslator = new BidTranslator();
    private LotTranslator lotTranslator = new LotTranslator();
    private BuyerTranslator buyerTranslator = new BuyerTranslator();
    private SellerTranslator sellerTranslator = new SellerTranslator();
    private ProductTranslator productTranslator = new ProductTranslator();

    // Loaders

    private BidLoader bidLoader = new BidLoader(bidRepository, bidTranslator);
    private LotLoader lotLoader = new LotLoader(lotRepository, lotTranslator);
    private BuyerLoader buyerLoader = new BuyerLoader(buyerRepository, buyerTranslator);
    private SellerLoader sellerLoader = new SellerLoader(sellerRepository, sellerTranslator);
    private ProductLoader productLoader = new ProductLoader(productRepository, productTranslator);

    // Data Source
    private DataSource dataSource;

    private AuctionService(){
        String repType = PropertiesProvider.getInstance().getApplicationProperties().getProperty("Repository_type");
        if (!repType.equals("Database")){
            bidRepository = new CollectionBidRepository();
            lotRepository = new CollectionLotRepository();
            buyerRepository = new CollectionBuyerRepository();
            sellerRepository = new CollectionSellerRepository();
            productRepository = new CollectionProductRepository();
        } else{
            bidRepository = new DatabaseBidRepository();
            lotRepository = new DatabaseLotRepository();
            buyerRepository = new DatabaseBuyerRepository();
            sellerRepository = new DatabaseSellerRepository();
            productRepository = new DatabaseProductRepository();
        }

        bidTranslator = new BidTranslator();
        lotTranslator = new LotTranslator();
        buyerTranslator = new BuyerTranslator();
        sellerTranslator = new SellerTranslator();
        productTranslator = new ProductTranslator();

        bidLoader = new BidLoader(bidRepository, bidTranslator);
        lotLoader = new LotLoader(lotRepository, lotTranslator);
        buyerLoader = new BuyerLoader(buyerRepository, buyerTranslator);
        sellerLoader = new SellerLoader(sellerRepository, sellerTranslator);
        productLoader = new ProductLoader(productRepository, productTranslator);
    }

    public static AuctionService getInstance(){
        if (instance == null)
            instance = new AuctionService();
        return instance;
    }

    public DateFormat getDateFormat(){
        return dateFormat;
    }

    public void setDateFormat(DateFormat newDateFormat){
        dateFormat = newDateFormat;
    }

    public BidRepository getBidRepository() {
        return bidRepository;
    }

    public LotRepository getLotRepository() {
        return lotRepository;
    }

    public BuyerRepository getBuyerRepository() {
        return buyerRepository;
    }

    public SellerRepository getSellerRepository() {
        return sellerRepository;
    }

    public ProductRepository getProductRepository() {
        return productRepository;
    }

    public BidTranslator getBidTranslator() {
        return bidTranslator;
    }

    public LotTranslator getLotTranslator() {
        return lotTranslator;
    }

    public BuyerTranslator getBuyerTranslator() {
        return buyerTranslator;
    }

    public SellerTranslator getSellerTranslator() {
        return sellerTranslator;
    }

    public ProductTranslator getProductTranslator() {
        return productTranslator;
    }

    public BidLoader getBidLoader() {
        return bidLoader;
    }

    public LotLoader getLotLoader() {
        return lotLoader;
    }

    public BuyerLoader getBuyerLoader() {
        return buyerLoader;
    }

    public SellerLoader getSellerLoader() {
        return sellerLoader;
    }

    public ProductLoader getProductLoader() {
        return productLoader;
    }

    // Operations with Buyer

    public Buyer getBuyerById(Long id){
        return buyerLoader.getEntity(id);
    }

    public void updateBuyer(BuyerValueObject buyer){
        buyerRepository.update(buyer);
    }

    public void deleteBuyerById(Long id){
        ArrayList<Long> bidIDs = new ArrayList<>();
        for (BidValueObject bidValueObject : bidRepository.getAll()) {
            if (bidValueObject.buyerId.equals(id)){
                bidIDs.add(bidValueObject.id);
            }
        };

        for (Long bidId : bidIDs){
            deleteBidById(bidId);
        }

        buyerRepository.delete(id);
    }

    public Collection<Buyer> getAllBuyers(){
        return buyerLoader.getAllEntities();
    }

    // Operations with Seller

    public Seller getSellerById(Long id){
        return sellerLoader.getEntity(id);
    }

    public void updateSeller(SellerValueObject seller){
        sellerRepository.update(seller);
    }

    public void deleteSellerById(Long id){
        Collection<ProductValueObject> productList = productRepository.getAll();
        if (productList != null) {
            ArrayList<ProductValueObject> products = new ArrayList<>();
            for (ProductValueObject productValueObject : productList) {
                if (productValueObject.ownerId.equals(id)) {
                    products.add(productValueObject);
                }
            }
            ;

            for (ProductValueObject productValueObject : products) {
                productValueObject.ownerId = (long) 0;
                productRepository.update(productValueObject);
            }
        }
        sellerRepository.delete(id);
    }

    public Collection<Seller> getAllSellers(){
        return sellerLoader.getAllEntities();
    }

    // Operations with Product

    public Product getProductById(Long id){
        return productLoader.getEntity(id);
    }

    public void updateProduct(ProductValueObject product){
        productRepository.update(product);
    }

    public void deleteProductById(Long id){
        Long lotId = (long)0;

        for (LotValueObject lotValueObject : lotRepository.getAll()){
            if (lotValueObject.productId.equals(id)){
                lotId = lotValueObject.id;
                break;
            }
        }

        if (lotId != 0)
            deleteLotById(lotId);
        productRepository.delete(id);
    }

    public Collection<Product> getAllProducts(){
        return productLoader.getAllEntities();
    }

    // Operations with Bid

    public Bid getBidById(Long id){
        return bidLoader.getEntity(id);
    }

    public BidValueObject createBid(Long id, Long buyerId, Long lotId, double bidAmount) {
        BidValueObject bidValueObject = new BidValueObject(id, lotId, buyerId, bidAmount);

        BuyerValueObject buyerValueObject = buyerRepository.find(buyerId);
        if (buyerValueObject == null){
            logger.info("Buyer is null. Unable to create a bid.");
            return null;
        }
        LotValueObject lotValueObject = lotRepository.find(lotId);
        if (lotValueObject == null){
            logger.info("Lot is null. Unable to create a bid.");
            return null;
        }

        if (buyerValueObject.accountBalance >= bidAmount && bidAmount >= lotValueObject.currentPrice) {
            BidValueObject oldBidValueObject = null;
            for(BidValueObject bidVO : bidRepository.getAll()) {
                if (bidVO.lotId.equals(lotValueObject.id))
                    oldBidValueObject = bidVO;
            }

            if (oldBidValueObject != null){
                if (oldBidValueObject.amount < bidAmount){
                    deleteBidById(oldBidValueObject.id);
                } else {
                    logger.info("Old bid amount is more than new bid amount.");
                    return null;
                }
            }

            lotValueObject.currentPrice = bidValueObject.amount;
            buyerValueObject.accountBalance -= bidAmount;
            buyerRepository.update(buyerValueObject);
            lotRepository.update(lotValueObject);
            bidRepository.add(bidValueObject);
        } else {
            logger.info("Buyer doesn't have enough money to make this bid.");
            return null;
        }

        logger.info("Bid with id = " + bidValueObject.id + " was added to repository.");
        return bidValueObject;
    }

    public void deleteBidById(Long id) {
        BidValueObject bidValueObject = bidRepository.find(id);
        if (bidValueObject == null){
            logger.info("Bid with id = " + id + " was not found.");
            return;
        }
        BuyerValueObject buyerValueObject = buyerRepository.find(bidValueObject.buyerId);
        System.out.println(buyerValueObject);
        if (buyerValueObject != null) {
            buyerValueObject.accountBalance += bidValueObject.amount;
            buyerRepository.update(buyerValueObject);
        }
        bidRepository.delete(id);
    }

    public void updateBid(BidValueObject bid){
        bidRepository.update(bid);
    }

    public Collection<Bid> getAllBids(){
        return bidLoader.getAllEntities();
    }

    // Operations with Lot

    public Lot getLotById(Long id){
        return lotLoader.getEntity(id);
    }

    public LotValueObject createLot(Long id, double startPrice, Date endDate, Long productId){
        LotValueObject lotValueObject = new LotValueObject(id, endDate, productId, startPrice);
        lotRepository.add(lotValueObject);

        Product product = productTranslator.convertToDomainObject(productRepository.find(productId));
        Lot lot = new Lot(lotValueObject.id, endDate, productLoader.getEntity(productId), startPrice);

//        Timer timer = new Timer();
//        timer.schedule(new LotHandler(lot.getId()), endDate.getTime() - (new Date()).getTime());
//        timers.put(lot.getId(), timer);

        System.out.println("Auction has started. Name: " + lot.getProduct().getTitle());

        return lotValueObject;
    }

    public void deleteLotById(Long id){
        LotValueObject lotValueObject = lotRepository.find(id);

        Long bidId = null;
        for(BidValueObject bidValueObject : bidRepository.getAll()){
            if (bidValueObject.lotId.equals(id)){
                bidId = bidValueObject.id;
                break;
            }
        }
        if (bidId != null)
            deleteBidById(bidId);

        lotRepository.delete(id);
    }

    public void updateLot(LotValueObject lot){
        lotRepository.update(lot);
    }

    public Collection<Lot> getAllLots(){
        return lotLoader.getAllEntities();
    }

    // Data Source

    public DataSource getDataSource(){
        if (dataSource == null){
            HikariConfig config = new HikariConfig(PropertiesProvider.getInstance().getDatabaseConnectionProperties());
            dataSource = new HikariDataSource(config);
        }
        return dataSource;
    }
}
