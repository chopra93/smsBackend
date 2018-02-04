package com.sms.core.vault;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author chopra
 * 12/11/17
 */

@Configuration("vaultSetup")
@DependsOn("startupService")
public class VaultSetup {
    private final static Logger LOGGER = LoggerFactory.getLogger(VaultSetup.class);

    private Vault vault;

    @Bean
    public Vault getVault() throws  Exception{
        VaultConfig config = new VaultConfig(VaultProperties.VAULT_SERVER_URL,VaultProperties.VAULT_TOKEN);
        vault = new Vault(config);
        return vault;
    }




}
