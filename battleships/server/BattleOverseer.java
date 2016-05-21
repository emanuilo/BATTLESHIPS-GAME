package battleships.server;

public class BattleOverseer extends Thread {
	private Game myGame;
	private int remainingTime;
	private WFPState wfp=new WFPState(myGame);
	private DeployState deploy=new DeployState(myGame);
	private RoundState round=new RoundState(myGame);
	private UpdateState update=new UpdateState(myGame);
	
	public BattleOverseer(Game game) {
		myGame=game;
	}
	@Override
	public void run() {
		
		try{
			while(!interrupted()){
				myGame.changeState(wfp);
			
				myGame.deployShipsInformation();
				sleep(1000);
				myGame.resendDeployShipsInformation();
				sleep(1000);
				myGame.resendDeployShipsInformation();
				myGame.deleteNotConfirmedPl();
				
				myGame.changeState(deploy);
				for (int i=myGame.getDeployTime()/1000;i>=0;i--){
					remainingTime=i;
					sleep(1000);
				}
				
				int roundCounter=0;
				while(true){
					myGame.changeState(round);
					myGame.roundInformation(++roundCounter);
					for (int i=myGame.getRoundTime()/1000;i>=0;i--){
						remainingTime=i;
						sleep(1000);
					}
					myGame.changeState(update);
					myGame.updateInformation();
					sleep(3000);
					if(myGame.getNumOfRemaining()<=1){
						this.interrupt();
						break;
					}
				}
			}
		}catch(InterruptedException e){}
	}
	
	public int getRemainingTime(){
		return remainingTime;
	}
}
