package com.angrysurfer.social.shrapnel.export.service.security;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class JasyptConfig {

	static String algorithm = "PBEWithMD5AndTripleDES";

	@Bean("jasyptStringEncryptor")
	public StringEncryptor stringEncryptor() {
		PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
		SimpleStringPBEConfig    config    = new SimpleStringPBEConfig();
		config.setPassword("blackops");
		config.setAlgorithm(algorithm);
		config.setKeyObtentionIterations(1000);
		config.setPoolSize(1);
//		config.setProviderName(“SunJCE”);
//		config.setSaltGeneratorClassName(“org.jasypt.salt.RandomSaltGenerator”);
//		config.setIvGeneratorClassName(“org.jasypt.iv.RandomIvGenerator”);
//		config.setStringOutputType(“base64”);
		encryptor.setConfig(config);
		return encryptor;
	}
}
