/*
 *
 *  * MIT License
 *  *
 *  * Copyright (c) 2020 Eduardo Alvarez , Alvaro Castillo , Ignacio Fuenzalida
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in all
 *  * copies or substantial portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

package cl.ucn.disc.pdis.parkingapp.repository.service;

import cl.ucn.disc.pdis.parkingucn.zeroice.model.Contratos;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.ContratosPrx;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.TheSystem;
import cl.ucn.disc.pdis.parkingucn.zeroice.model.TheSystemPrx;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.InitializationData;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Properties;
import com.zeroc.Ice.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The ZeroIce class.
 */
@SuppressWarnings("Singleton")
public final class ZeroIce {

    //The Logger.
    private static final Logger log = LoggerFactory.getLogger(ZeroIce.class);

    //The Singleton.
    private static final ZeroIce ZERO_ICE = new ZeroIce();


    // The ZeroIce communicator.
    private Communicator theCommunicator;

    //The Contratos implementation.
    private ContratosPrx theContratos;

    //The System implementation.
    private TheSystemPrx theSystem;

    /**
     * The Constructor.
     */
    public ZeroIce() {
    }

    /**
     * Get Instance.
     *
     * @return the ZeroIce.
     */
    public static ZeroIce getInstance() {
        return ZERO_ICE;
    }

    /**
     * Get Contratos.
     *
     * @return the Contratos.
     */
    public ContratosPrx getContratos() {
        return this.theContratos;
    }

    public TheSystemPrx getTheSystem() {
        return this.theSystem;
    }

    /**
     * Start the Communications.
     */
    public void start() {
        if (this.theCommunicator != null) {
            log.warn("The Communicator was already initialized?");
            return;
        }
        String[] args = {"one", "two"};
        this.theCommunicator = Util.initialize(getInitializationData(args));


        // The name of Contratos
        String name = Contratos.class.getSimpleName();
        log.debug("Proxying <{}> ..", name);

        // The proxy 4 TheContratos
        ObjectPrx theProxy = this.theCommunicator.stringToProxy(name + ":tcp -h 192.168.0.15 -p 4000 -t 15000 -z");

        // Trying to cast the proxy
        this.theContratos = ContratosPrx.checkedCast(theProxy);

        // The name
        name = TheSystem.class.getSimpleName();
        log.debug("Proxying <{}> ..", name);

        // The proxy 4 TheContratos
        theProxy = this.theCommunicator.stringToProxy(name + ":tcp -h 192.168.0.15 -p 4020 -t 15000 -z");

        this.theSystem = TheSystemPrx.checkedCast(theProxy);
    }

    /**
     * Initialization of ZeroIce.
     *
     * @param args to use as source.
     * @return the {@link InitializationData}.
     */
    private static InitializationData getInitializationData(String[] args) {

        // Properties
        final Properties properties = Util.createProperties(args);
        properties.setProperty("Ice.Package.model", "cl.ucn.disc.pdis.parkingucn.zeroice");

        // https://doc.zeroc.com/ice/latest/property-reference/ice-trace
        // properties.setProperty("Ice.Trace.Admin.Properties", "1");
        // properties.setProperty("Ice.Trace.Locator", "2");
        // properties.setProperty("Ice.Trace.Network", "3");
        // properties.setProperty("Ice.Trace.Protocol", "1");
        // properties.setProperty("Ice.Trace.Slicing", "1");
        // properties.setProperty("Ice.Trace.ThreadPool", "1");
        // properties.setProperty("Ice.Compression.Level", "9");
        // properties.setProperty("Ice.Plugin.Slf4jLogger.java",
        // "cl.ucn.disc.pdis.parkingapp.zeroice.Slf4jLoggerPluginFactory");
        InitializationData initializationData = new InitializationData();
        initializationData.properties = properties;

        return initializationData;
    }


    /**
     * Stop the communications.
     */
    public void stop() {
        if (this.theCommunicator == null) {
            log.warn("The Communicator was already stopped?");
            return;
        }
        this.theContratos = null;
        this.theSystem = null;
        this.theCommunicator.destroy();

    }

}
