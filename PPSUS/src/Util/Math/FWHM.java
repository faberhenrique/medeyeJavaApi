/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util.Math;

import org.apache.commons.math3.fitting.*;

/**
 *
 * @author Faber
 */
public class FWHM {
    double data[];
    private double bestFit[];
    public FWHM(double vet[]){
        data = vet;
    }
    public double[] calcFWHM(){
        GaussianCurveFitter fitter = GaussianCurveFitter.create();

        WeightedObservedPoints obs = new WeightedObservedPoints();

        for (int index = 0; index < data.length; index++) {
            obs.add(data[index], index);
        }

        bestFit = fitter.fit(obs.toList());
        return bestFit;
    }
}
