package oneproject;

/**
 * Some sample OpenChord code.  See OpenChord manual and javadocs for more detail.
 */
import java.net.InetAddress;
import java.net.MalformedURLException;
import de.uniba.wiai.lspi.chord.data.URL;
import de.uniba.wiai.lspi.chord.service.*;
import de.uniba.wiai.lspi.chord.service.impl.*;

import java.util.*;
import java.io.Serializable;

public class ChordClient {

	Chord chord;
// ChordClient.....createNetwork...joinNetwork....main
    public ChordClient(String masterHost, boolean master)
    {
		try {
			if(master) {
				this.createNetwork(masterHost);

			} else {
				this.joinNetwork(masterHost);				
			}	
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
    
    /**
     * This is called by the "bootstrap" client, aka as the "master".
     * 
     * @param host The name of the current host that we are bootstrapping on.
     */
	public void createNetwork(String host)
	{
		System.out.println(">>>>>>>>Creating Chord network on [" + host + "]");
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);
                //
                System.out.println("protocol :"+protocol);
		URL localURL = null;
		try {
			localURL = new URL( protocol + "://" + host + ":8080/");
                        System.out.println("localURL :"+localURL);
		} catch ( MalformedURLException e){
			throw new RuntimeException (e);
		}
		
		this.chord = new ChordImpl();
	
		try {
			this.chord.create( localURL );		
			
		} catch ( ServiceException e) {
			throw new RuntimeException (" Could not create DHT!", e);
		}
		
	}
	
	/**
	 * This is called by all the other non-bootstrap clients to join the p2p network. 
	 * 
	 * @param host The name of the host the bootstrap client is running on.
	 */
	public void joinNetwork(String host)
	
	{
		System.out.println(">>>>>>>>Joining Chord network on [" + host + "]");		
		String protocol = URL.KNOWN_PROTOCOLS.get(URL.SOCKET_PROTOCOL);		
		URL localURL = null;
		try {
			
                    
			localURL = new URL( protocol + "://" + InetAddress.getLocalHost().getHostAddress() + "/");			
                    System.err.println("localURL :"+localURL);
                    System.err.println(""+InetAddress.getLocalHost().getHostAddress());
                   
		} catch ( MalformedURLException e){
			throw new RuntimeException (e);
		} catch ( Exception ex) {
			throw new RuntimeException (ex);
		}
		
		URL bootstrapURL = null;
		try {
			bootstrapURL = new URL( protocol + "://" + host + ":8080/");
                        System.err.println("boots :"+bootstrapURL);
		} catch ( MalformedURLException e){
			throw new RuntimeException (e);
		}
		this.chord = new ChordImpl();
		try {
			this.chord.join( localURL , bootstrapURL );
		} catch ( ServiceException e) {
                  //  e.printStackTrace();
			throw new RuntimeException (" Could not join DHT!", e);
		}		
					
	}    
	
	/**
	 * @param args
	 */
	public static void insert_retrieve(ChordClient client,boolean theMaster,Object file,String fileName) {
		
	
		
		// Step 3: Create or join Chord network and do some key insert/retrieve operations.		
		try {
			

			if( theMaster) {
				
				// ok, just for fun we'll create a key and insert a value into the DHT.
                            
				String keyVal = fileName;
				Object data =  file;
                                
				StringKey myKey = new StringKey ( keyVal );
                                if(myKey==null)
                                    System.out.println("MyKey is null");
                                
                                 if(data==null)
                                    System.out.println("data is null");
				client.chord.insert(myKey, (Serializable) data);
	System.out.println("content File :"+file);
				// We want to keep the master process around long enough to start up a second
				// process to read what we just inserted!
				System.out.println("Waiting");
				System.in.read();
				System.out.println("Master client is existing");

				
				
			} else {
				
				
				//  try to retrieve the data the master node inserted.
				StringKey myKey = new StringKey(fileName);
				
				// notice that we get a set of vals back... but if we make sure
				// only one item is inserted per key, we'll only get one item in our set.
				Set<Serializable> vals = client.chord.retrieve(myKey);
                                System.out.println("cccccc"+vals);
				Iterator<Serializable> it = vals.iterator();
				while(it.hasNext()) {
					String data = (String) it.next();
					System.out.println("Got [" + data + "]");
                                        FileInput.writeObjectFromFile(data);
				}
				
				System.out.println("Client is exiting.");
			}
			
			// Step 4: client should inform the Chord network it is leaving, so it
			// can rearrange itself.
			client.chord.leave();
			
			
		} catch ( ServiceException e) {
			throw new RuntimeException (" Could not create DHT!", e);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
