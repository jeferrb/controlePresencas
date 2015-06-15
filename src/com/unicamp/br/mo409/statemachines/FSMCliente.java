package com.unicamp.br.mo409.statemachines;

import nz.ac.waikato.modeljunit.*;
import nz.ac.waikato.modeljunit.coverage.*;

public class FSMCliente implements FsmModel{
	
	private String state;
	

	public FSMCliente() {
		this.state = StateCliente.inativo.toString();
	}

	@Override
	public Object getState() {
		return String.valueOf(state);
	}

	public void setState(String parm) {
		this.state = parm;
	}

	@Override
	public void reset(boolean bln) {
		state = StateCliente.inativo.toString();
	}
	
	
	public boolean loginClienteGuard() {
		return state.equals(StateCliente.inativo.toString());

	}

	public @Action
	void loginCliente() {
		System.out.println("loginCliente: " + state + " --> "
				+ StateCliente.logado.toString());
		state = StateCliente.logado.toString();
	}

	public boolean loginClienteInvalidoGuard() {
		return state.equals(StateCliente.inativo.toString());
	}

	public @Action
	void loginClienteInvalido() {
		System.out.println("loginClienteInvalido: " + state + " --> "
				+ StateCliente.inativo.toString());
				state = StateCliente.inativo.toString();
	}
	
	public boolean efetuarLogoutClienteGuard() {
		return state.equals(StateCliente.logado.toString());
	}

	public @Action
	void efetuarLogoutCliente() {
		System.out.println("efetuarLogoutCliente: " + state + " --> "
				+ StateCliente.logado.toString());
		state = StateCliente.inativo.toString();
	}
	
	
	public static void main(String[] args) {
		// create our model and a test generation algorithm
				Tester tester = new GreedyTester(new FSMCliente());

				// build the complete FSM graph for our model, just to ensure
				// that we get accurate model coverage metrics.
				tester.buildGraph();

				// set up our favourite coverage metric
				CoverageMetric trCoverage = new TransitionCoverage();
				tester.addCoverageMetric(trCoverage);

				// ask to print the generated tests
				tester.addListener("verbose", new VerboseListener(tester.getModel()));

				// generate a small test suite of 20 steps (covers 4/5 transitions)
				tester.generate(20);

				tester.getModel().printMessage(
						trCoverage.getName() + " was " + trCoverage.toString());

	}

}
