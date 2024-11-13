package Phase3.MyBackend.MySolvers;

import static Phase3.Data3D.MyData.depth;
import static Phase3.Data3D.MyData.height;
import static Phase3.Data3D.MyData.width;

import Phase3.MyBackend.Container;

/**
 * The ISolver interface defines the contract for solvers in the context of the 3D packing problem.
 * It includes a constant Container object 'c' representing the packing space and a method 'solve()' 
 * that should be implemented by concrete solver classes.
 */
public interface ISolver {
    
    /**
     * The constant Container object representing the packing space.
     */
    Container c =new Container(width,height,depth);
    
    /**
     * Solves the 3D packing problem.
     *
     * @return true if the problem is successfully solved, false otherwise.
     */
    boolean solve();
}