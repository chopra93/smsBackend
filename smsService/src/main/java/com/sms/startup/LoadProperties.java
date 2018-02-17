package com.sms.startup;

import com.bettercloud.vault.VaultException;
import com.sms.core.objects.PaymentDTO;
import com.sms.service.IPaymentService;
import com.sms.service.IRedisService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

/**
 * @author chopra
 */

@Service("loadProperties")
public class LoadProperties {


  @Autowired
  private IRedisService redisService;

  @Autowired
  private IPaymentService paymentService;

  private static final Logger LOG = Logger.getLogger(LoadProperties.class);

  @PostConstruct
  public void loadAll() throws IOException, VaultException {

    loadAllCache();
  }

  public void reloadCache() throws IOException, VaultException {
    loadAllCache();
  }

  private void loadAllCache() throws IOException, VaultException {

    loadDocMProperties();

    loadUniqueDataCache();

    loadPaymentUrl();
  }

  private void loadPaymentUrl(){
    LOG.info("loading payment url");
    List<PaymentDTO> paymentDTOList = paymentService.fetchPaymentList();
    for(PaymentDTO paymentDTO: paymentDTOList){
      redisService.setValue(paymentDTO.getType(),paymentDTO.getPaymentUrl());
    }
    LOG.info("loaded payment url");
  }

  private void loadUniqueDataCache() {
//    LOG.info("loading server specific unique data in cache...");
//    String serverSpecificUUID = UUID.randomUUID().toString();
//    StringBuilder serverValidationTokenBuilder = new StringBuilder();
//
//    Random rand = new Random();
//    for (int i = 0; i < 8; i++) {
//      serverValidationTokenBuilder.append(serverSpecificUUID.charAt(rand.nextInt(36)));
//    }
//
//    UniqueDataCache uniqueDataCache = new UniqueDataCache();
//    uniqueDataCache.addUniqueDataForServer(Type.VALIDATION_TOKEN_PREFIX,
//        serverValidationTokenBuilder.toString());
//
//    CacheManager.getInstance().setCache(uniqueDataCache);
    LOG.info("loaded server specific unique data in cache...");
  }

  private void loadDocMProperties() throws IOException, VaultException {
//    LOG.info("loading DocM properties data in cache...");
//
//    DmPropertyCache propCache = new DmPropertyCache();
//
//    List<DmProperty> vaultDataList = getVaultProperties();
//    List<DmProperty> docMPropertyList = docMService.fetchDocMProperty();
//    docMPropertyList.addAll(vaultDataList);
//    propCache.addDmPropertyData(docMPropertyList);
//
//    CacheManager.getInstance().setCache(propCache);
//
//    LOG.info("loaded DocM properties data in cache...");
  }



}
