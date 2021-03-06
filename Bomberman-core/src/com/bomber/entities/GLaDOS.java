package com.bomber.entities;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Array;
import com.bomber.*;
import handlers.*;

public class GLaDOS {
	
	private final int WALL = -2;
	private final int BOMB = -1;
	private final int EXPLOSION = -3;
	private final int PLAYER = 100;
	
	private MyContactListener cl;
	
	private int nAlive;
	private int x_player;
	private int y_player;
	
	private Enemy enemy1;
	private Enemy enemy2;
	private Enemy enemy3;
	
	private int[][] position;
	private int[][] risk;
	
	private ArrayList<Bomb> bombs;
	
	public GLaDOS(Enemy e1, Enemy e2, Enemy e3) {
		nAlive = 3;
		this.enemy1 = e1;
		this.enemy2 = e2;
		this.enemy3 = e3;
		position = new int[14][14];
		risk = new int[14][14];
	}	
	
	public void moveEnemy(Player player, MyContactListener cl, ArrayList<Bomb> bombs) {
		this.bombs = bombs;
		//positionBombs();
		x_player = (int) player.getPosition().x/32 - 1;
		y_player = (int) player.getPosition().y/32 - 1;
		this.cl = cl;
		
		createMatrix();
		//printMatrix(0,0);
		// Verificar quantos inimigos estão vivos
		if(nAlive == 3) {
			this.moveEnemy3();
			this.moveEnemy2();
			this.moveEnemy1();
			
		} else if (nAlive == 2) {
			if(!cl.isEnemy3dead() && enemy3.isStatic())
				this.moveEnemy3();
			if(!cl.isEnemy2dead() && enemy2.isStatic())
				this.moveEnemy2();
			if (!cl.isEnemy1dead() && enemy1.isStatic())
				this.moveEnemy1();
			
		} else {
			if(!cl.isEnemy3dead() && enemy3.isStatic())
				this.moveEnemy3();
			else if(!cl.isEnemy2dead() && enemy2.isStatic())
				this.moveEnemy2();
			else if (!cl.isEnemy1dead() && enemy1.isStatic())
				this.moveEnemy1();
		} 
		
	}
	
	private void printMatrix(int y, int x) {
		System.out.println(" ");
		for(int i = 0; i < 13; i++){
			for(int j = 0; j < 13; j++) {
				if(position[i][j] == -2)
					System.out.print("p   ");
				else if(position[i][j] == PLAYER)
					System.out.print("P ");
				else if(i == x && j == y)
					System.out.print("B ");
				else
					System.out.print(position[j][i] + "   ");
			}
			System.out.println(" ");
		}
			
	}
	
	/** Retorna uma matriz com 0 aonde não existe risco, -1 aonde existe
	 * e -2 onde existe parede */
	private void positionBombs() {
		
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 13; j++)  {
				if((i+1)%2 == 0 && (j+1)%2 == 0) 
					risk[i][j] = WALL;
				else
					risk[i][j] = 0; 	
			}
		}
		
		if(bombs.isEmpty())
			return;
		
		for(Bomb b : bombs) 
			risk[(int)(b.getPosition().x-16)/32 - 1][(int)(b.getPosition().y-16)/32 - 1] = BOMB;
		
		
		
		for(int i = 0; i < 13; i++) {
			for(int j = 0; j < 13; j++) {
				if(risk[i][j] == BOMB) {
					// Marcando dois para cima
					if(j < 13 && risk[i][j+1] != WALL) {
						risk[i][j+1] = EXPLOSION;
						if(j < 12 && risk[i][j+2] != WALL)
							risk[i][j+2] = EXPLOSION;
					}
					
					// Marcando dois para baixo
					if(j > 0 && risk[i][j-1] != WALL) {
						risk[i][j-1] = EXPLOSION;
						if(j > 1 && risk[i][j-2] != WALL)
							risk[i][j-2] = EXPLOSION;
					}
					
					// Marcando dois para a esquerda
					if(i > 0 && risk[i-1][j] != WALL) {
						risk[i-1][j] = EXPLOSION;
						if(i > 1 && risk[i-2][j] != WALL)
							risk[i-2][j] = EXPLOSION;
					}
					
					// Marcando dois para a direita
					if(i < 13 && risk[i+1][j] != WALL) {
						risk[i+1][j] = EXPLOSION;
						if(i < 12 && risk[i+2][j] != WALL)
							risk[i+2][j] = EXPLOSION;
					}
				}
			} 
		}
	}	

	
	/* Calcula a distância entre cada ponto do mapa e o bomberman */
	void createMatrix() {
	    int i, j, step=1, num=0;
	    
	    for(i = 0; i < 13; i++) {
			for(j = 0; j < 13; j++)  {
				if((i+1)%2 == 0 && (j+1)%2 == 0) 
					position[i][j] = WALL;
				else
					position[i][j] = 0; 	
			}
		}
	    
	    /* Contando quantos espaços vazios existem */
	    for(i=0; i<13; i++)
	        for(j=0; j<13; j++)
	            if(position[i][j]==0)
	                num++;

	    /* Colocando 1 nas posições ao redor do queijo */
	    if(x_player > 0 && position[x_player-1][y_player] == 0) {
	    	position[x_player-1][y_player] = 1;
	        num--;
	    }
	    if(x_player < 13 && position[x_player+1][y_player] == 0) {
	    	position[x_player+1][y_player] = 1;
	        num--;
	    }
	    if(y_player > 0 && position[x_player][y_player-1] == 0) {
	    	position[x_player][y_player-1] = 1;
	        num--;
	    }
	    if(y_player < 13 && position[x_player][y_player+1] == 0) {
	    	position[x_player][y_player+1] = 1;
	        num--;
	    }

	    /* Preenchendo a matriz de custo */
	    while(num > 0) {
	        for(i=0; i<13; i++) {
	            for(j=0; j<13; j++) {
	                if(position[i][j]==step) {
	                    if(i < 13 && position[i+1][j]==0) {
	                    	position[i+1][j] = step+1;
	                        num--;
	                    }
	                    if(i > 0 && position[i-1][j]==0) {
	                    	position[i-1][j] = step+1;
	                        num--;
	                    }
	                    if(j < 13 && position[i][j+1]==0) {
	                    	position[i][j+1] = step+1;
	                        num--;
	                    }
	                    if(j > 0 && position[i][j-1]==0) {
	                    	position[i][j-1] = step+1;
	                        num--;
	                    }
	                }
	            }
	        }
	    step++;
	    }
	    position[y_player][x_player]=PLAYER;
	}
	
	private void moveEnemy1() {
		// Pegando a posição do inimigo
		int x_enemy = (int) enemy1.getPosition().x/32 - 1;
		int y_enemy = (int) enemy1.getPosition().y/32 - 1;
		int direction = 0, place = 100;
		
		/** Vendo se ele está em uma zona de risco */
		if(risk[x_enemy][y_enemy] == BOMB || risk[x_enemy][y_enemy] == EXPLOSION) {
			direction = this.ifRisk(enemy1);
		} 
		/** Se ele não estiver em uma zona de risco */
		else {
			// Olhando em volta
			if(y_enemy > 0 && position[x_enemy][y_enemy-1] < place
					&& position[x_enemy][y_enemy-1] > 0) {
				// A esquerda da matriz
				place = position[x_enemy][y_enemy-1];
				direction = enemy1.DOWN;
			}
			if(y_enemy < 13 && position[x_enemy][y_enemy+1] < place
					&& position[x_enemy][y_enemy+1] > 0) {
				// A direita da matriz
				place = position[x_enemy][y_enemy+1];
				direction = enemy1.UP;
			}
			if(x_enemy > 0 && position[x_enemy-1][y_enemy] < place
					&& position[x_enemy-1][y_enemy] > 0) {
				// Para cima da matriz
				place = position[x_enemy-1][y_enemy];
				direction = enemy1.LEFT;
			}
			if(x_enemy < 13 && position[x_enemy+1][y_enemy] < place
					&& position[x_enemy+1][y_enemy] >0) {
				// Para baixo da matriz
				place = position[x_enemy+1][y_enemy];
				direction = enemy1.RIGHT;
			}
		}
		
		/** Movimento do inimigo */
		if(position[(int)enemy1.getPosition().x/32-1][(int)enemy1.getPosition().y/32-1] == 1) 
			direction = 0;
		if(direction == enemy1.UP && enemy1.isStatic())
			enemy1.moveUp();
		
		else if(direction == enemy1.LEFT && enemy1.isStatic())
			enemy1.moveLeft();
		
		else if(direction == enemy1.RIGHT && enemy1.isStatic())
			enemy1.moveRight();
		
		else if(direction == enemy1.DOWN && enemy1.isStatic())
			enemy1.moveDown();
	}
	
	
	private int ifRisk(Enemy e) {
		int direction, x_enemy, y_enemy, distance = position[x_player][y_player];
		
		x_enemy = (int)e.getPosition().x/32 - 1;
		y_enemy = (int) e.getPosition().y/32 - 1;
		
		/** Vendo se tem lugar seguro ao redor, indo preferencialmente
		 * na direção do player */
		if(y_enemy < 13 && risk[x_enemy][y_enemy+1] > 0 &&
				position[x_enemy][y_enemy+1] < distance) {
			direction = e.UP; // Ir à direita na matriz
		}
		else if(y_enemy > 0 && risk[x_enemy][y_enemy-1] > 0 &&
				position[x_enemy][y_enemy-1] < distance) {
			direction = e.DOWN; // Ir à esquerda na matriz
		}
		else if(x_enemy < 13 && risk[x_enemy+1][y_enemy] > 0 &&
				position[x_enemy+1][y_enemy] < distance) {
			direction = e.RIGHT; // Ir para baixo na matriz
		}
		else if(x_enemy > 0 && risk[x_enemy-1][y_enemy] > 0 &&
				position[x_enemy-1][y_enemy] < distance) {
			direction = e.LEFT; // Ir para cima na matriz
		} else {
			direction = e.getLastState();
		}	
		
		return direction;
	}
	

	private void moveEnemy2() {
		// Pegando a posição do inimigo
		int x_enemy = (int) enemy2.getPosition().x/32 - 1;
		int y_enemy = (int) enemy2.getPosition().y/32 - 1;
		int direction = 0, place = 100;
		
		/** Vendo se ele está em uma zona de risco */
		if(risk[x_enemy][y_enemy] == BOMB || risk[x_enemy][y_enemy] == EXPLOSION) {
			direction = this.ifRisk(enemy2);
		} 
		/** Se ele não estiver em uma zona de risco */
		else {
			// Olhando em volta
			if(x_enemy < 13 && position[x_enemy+1][y_enemy] < place
					&& position[x_enemy+1][y_enemy] >0) {
				// Para baixo da matriz
				place = position[x_enemy+1][y_enemy];
				direction = enemy2.RIGHT;
			}
			if(y_enemy < 13 && position[x_enemy][y_enemy+1] < place
					&& position[x_enemy][y_enemy+1] > 0) {
				// A direita da matriz
				place = position[x_enemy][y_enemy+1];
				direction = enemy2.UP;
			}
			if(y_enemy > 0 && position[x_enemy][y_enemy-1] < place
					&& position[x_enemy][y_enemy-1] > 0) {
				// A esquerda da matriz
				place = position[x_enemy][y_enemy-1];
				direction = enemy2.DOWN;
			}
			if(x_enemy > 0 && position[x_enemy-1][y_enemy] < place
					&& position[x_enemy-1][y_enemy] > 0) {
				// Para cima da matriz
				place = position[x_enemy-1][y_enemy];
				direction = enemy2.LEFT;
			}
		}
		/** Movimento do inimigo */
		if(position[(int)enemy2.getPosition().x/32-1][(int)enemy2.getPosition().y/32-1] == 1) 
			direction = 0;
		
		if(direction == enemy2.UP && enemy2.isStatic())
			enemy2.moveUp();
		
		else if(direction == enemy2.LEFT && enemy2.isStatic())
			enemy2.moveLeft();
		
		else if(direction == enemy2.RIGHT && enemy2.isStatic())
			enemy2.moveRight();
		
		else if(direction == enemy2.DOWN && enemy2.isStatic())
			enemy2.moveDown();
	}
	
	
	private void moveEnemy3() {
		// Pegando a posição do inimigo
		int x_enemy = (int) enemy3.getPosition().x/32 - 1;
		int y_enemy = (int) enemy3.getPosition().y/32 - 1;
		int direction = 0, place = 100;
		
		/** Vendo se ele está em uma zona de risco */
		if(risk[x_enemy][y_enemy] == BOMB || risk[x_enemy][y_enemy] == EXPLOSION) {
			direction = this.ifRisk(enemy3);
		} 
		/** Se ele não estiver em uma zona de risco */
		else {
			// Olhando em volta
			if(x_enemy > 0 && position[x_enemy-1][y_enemy] < place
					&& position[x_enemy-1][y_enemy] > 0) {
				// Para cima da matriz
				place = position[x_enemy-1][y_enemy];
				direction = enemy3.LEFT;
			}
			if(x_enemy < 13 && position[x_enemy+1][y_enemy] < place
					&& position[x_enemy+1][y_enemy] >0) {
				// Para baixo da matriz
				place = position[x_enemy+1][y_enemy];
				direction = enemy3.RIGHT;
			}
			if(y_enemy < 13 && position[x_enemy][y_enemy+1] < place
					&& position[x_enemy][y_enemy+1] > 0) {
				// A direita da matriz
				place = position[x_enemy][y_enemy+1];
				direction = enemy3.UP;
			}
			if(y_enemy > 0 && position[x_enemy][y_enemy-1] < place
					&& position[x_enemy][y_enemy-1] > 0) {
				// A esquerda da matriz
				place = position[x_enemy][y_enemy-1];
				direction = enemy3.DOWN;
			}
		}
		
		/** Movimento do inimigo */
		if(position[(int)enemy3.getPosition().x/32-1][(int)enemy3.getPosition().y/32-1] == 1) 
			direction = 0;
		
		if(direction == enemy3.UP && enemy3.isStatic())
			enemy3.moveUp();
		
		else if(direction == enemy3.LEFT && enemy3.isStatic())
			enemy3.moveLeft();
		
		else if(direction == enemy3.RIGHT && enemy3.isStatic())
			enemy3.moveRight();
		
		else if(direction == enemy3.DOWN && enemy3.isStatic())
			enemy3.moveDown();
	}
	
	
	public static void swapRows(int[][] m) {
        for (int  i = 0, k = m.length - 1; i < k; ++i, --k) {
            int[] x = m[i];
            m[i] = m[k];
            m[k] = x;
        }
    }
	
	public int getNumVivos() { return nAlive; }
	public void setNumVivos() { this.nAlive = 1; }
	public void died() { nAlive--; }
	
}



/** Calcula o menor caminho de um ponto até cada ponto do mapa 
private void smallPath(int xo, int yo) {
	PriorityQueue<Vector2> queue = new PriorityQueue<Vector2>();
	//Stack<Vector2> stack = new Stack<Vector2>();
	Vector2 v;
	int steps;
	
	for(int i = 0; i < 13; i++) {
		for(int j = 0; j < 13; j++)  {
			if((i+1)%2 == 0 && (j+1)%2 == 0) 
				position[i][j] = WALL;
			else
				position[i][j] = 0; 	
		}
	}
	
	queue.add(new Vector2(xo, yo));
	//stack.push(new Vector2(xo, yo));
	position[xo][yo] = 0;
	
	while(queue.peek() != null) {
		v = queue.poll();
		int i = (int)v.x, j = (int)v.y;
		steps = position[i][j];
		
		if(j < 13) {
			// Olhando para cima do ponto
			if(position[i][j + 1] > steps + 1 || position[i][j + 1] == 0) {
				queue.add(new Vector2(i, j + 1));
				position[i][j + 1] = steps + 1;
			}
		}
		
		if(j > 0) {
			// Olhando para baixo do ponto
			if(position[i][j - 1] > steps + 1 || position[i][j - 1] == 0) {
				queue.add(new Vector2(i, j - 1));
				position[i][j - 1] = steps + 1;
			}
		}
		
		if(i < 13) {
			// Olhando para a direita do ponto
			if(position[i + 1][j] > steps + 1 || position[i + 1][j] == 0) {
				queue.add(new Vector2(i + 1, j));
				position[i + 1][j] = steps + 1;
			}
		}
		
		if(i > 0) {
			// Olhando para a esquerda do ponto
			if(position[i - 1][j] > steps + 1 || position[i - 1][j] == 0) {
				queue.add(new Vector2(i - 1, j));
				position[i - 1][j] = steps + 1;
			}
		}
	}
}

 public void move(Enemy e) {
		int direction = 0, x, y, distance = position[x_player][y_player];
		int x_enemy, y_enemy, lastX = 0, lastY = 0;
		x_enemy = (int)e.getPosition().x/32 - 1;
		y_enemy = (int) e.getPosition().y/32 - 1;
		
		/** Vendo se ele está em uma zona de risco 
		if(risk[x_enemy][y_enemy] == BOMB || risk[x_enemy][y_enemy] == EXPLOSION) {
			direction = this.ifRisk(e);
		} 
		/** Se ele não estiver em uma zona de risco 
		else {
			x = x_player;
			y = y_player;
			while(x != x_enemy && y != y_enemy) {
				distance--;
				lastX = x;
				lastY = y;
				// Olhando qual o menor caminho
				if(y < 13 && position[x][y+1] == distance) y++;
				else if(y > 0 && position[x][y-1] == distance) y--;
				else if(x < 13 && position[x+1][y] == distance)	x++;
				else x--;
			}
			
			/** Se o menor caminho tiver bomba, ele escolhe qualquer outro sem 
			if(risk[lastX][lastY] == BOMB || risk[lastX][lastY] == EXPLOSION) {
				direction = this.ifRisk(e);
			}
			/** Verificando para onde deve andar 
			else if(y > lastY) direction = e.DOWN; // Ir à esquerda na matriz
			else if(y < lastY) direction = e.UP; // Ir à direita na matriz 
			else if(x > lastX) direction = e.LEFT; // Ir para cima na matriz
			else if(x < lastX) direction = e.RIGHT; // Ir para baixo na matriz
			else direction = 0;
			
			
		}
		

		/** Movimento do inimigo 
		if(direction == e.UP && e.isStatic())
			e.moveUp();
		
		else if(direction == e.LEFT && e.isStatic())
			e.moveLeft();
		
		else if(direction == e.RIGHT && e.isStatic())
			e.moveRight();
		
		else if(direction == e.DOWN && e.isStatic())
			e.moveDown();
		
	}
 *
 */

/**
private void printMatrix(int y, int x) {
	System.out.println(" ");
	for(int i = 0; i < 13; i++){
		for(int j = 0; j < 13; j++) {
			if(position[i][j] == -2)
				System.out.print("p   ");
			else if(position[i][j] == PLAYER)
				System.out.print("P ");
			else if(i == x && j == y)
				System.out.print("B ");
			else
				System.out.print(position[j][i] + "   ");
		}
		System.out.println(" ");
	}
		
}
*/