package com.example.pki.keystores;

import lombok.Getter;
import lombok.Setter;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;

import java.io.*;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Getter
@Setter
public class KeyStoreWriter {
	//KeyStore je Java klasa za citanje specijalizovanih datoteka koje se koriste za cuvanje kljuceva
	//Tri tipa entiteta koji se obicno nalaze u ovakvim datotekama su:
	// - Sertifikati koji ukljucuju javni kljuc
	// - Privatni kljucevi
	// - Tajni kljucevi, koji se koriste u simetricnima siframa
	private KeyStore keyStore;
	
	public KeyStoreWriter() {
		try {
			keyStore = KeyStore.getInstance("JKS", "SUN");
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		}
	}
	
	public void loadKeyStore(String fileName, char[] password) {
		try {
			if(fileName != null) {
				keyStore.load(new FileInputStream(fileName), password);
			} else {
				//Ako je cilj kreirati novi KeyStore poziva se i dalje load, pri cemu je prvi parametar null
				keyStore.load(null, password);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			try {
				keyStore.load(null, password);
			} catch (IOException ex) {
				ex.printStackTrace();
			} catch (NoSuchAlgorithmException ex) {
				ex.printStackTrace();
			} catch (CertificateException ex) {
				ex.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveKeyStore(String fileName, char[] password) {
		try {
			keyStore.store(new FileOutputStream(fileName), password);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void write(String alias, PrivateKey privateKey, char[] password, Certificate certificate) {
		try {
			keyStore.setKeyEntry(alias, privateKey, password, new Certificate[] {certificate});
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
	}

	public void delete(String alias) throws KeyStoreException {
		keyStore.deleteEntry(alias);
	}

	public List<Certificate> findAllSubs(String parentAlias) {
		List<Certificate> certificates = new ArrayList<>();
		try {
			Enumeration<String> aliases = keyStore.aliases();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				Certificate cert = readCertificate(alias);
				X500Name subject = new JcaX509CertificateHolder((X509Certificate) cert).getSubject();
				RDN pseudonymRDN = subject.getRDNs(BCStyle.PSEUDONYM)[0];
				String pseudonym = IETFUtils.valueToString(pseudonymRDN.getFirst().getValue());

				if(pseudonym.equals("endEntity"))
					return new ArrayList<>();

				if (cert != null) {
					X500Name issuer = new JcaX509CertificateHolder((X509Certificate) cert).getIssuer();
					RDN issuerAliasRDN = issuer.getRDNs(BCStyle.UID)[0];
					String issuerAlias = IETFUtils.valueToString(issuerAliasRDN.getFirst().getValue());
					if(issuerAlias.equals(parentAlias))
						certificates.add(cert);
				}
			}
		} catch (KeyStoreException | CertificateEncodingException e) {
			e.printStackTrace();
		}

		return certificates;
	}

	public Certificate readCertificate(String alias) {
		try {
			if(keyStore.isKeyEntry(alias)) {
				Certificate cert = keyStore.getCertificate(alias);
				return cert;
			}
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return null;
	}
}
