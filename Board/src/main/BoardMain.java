package main;

import java.util.List;
import java.util.Scanner;

import service.BoardServiceImpl;
import service.IBoardService;
import vo.BoardVO;

public class BoardMain {
	
	private Scanner sc = new Scanner(System.in);
	
	private IBoardService boardService;
	
	public BoardMain() {
		boardService = new BoardServiceImpl();
	}
	
	//전체 목록
	public void displayMenu() {
		System.out.println("________Display Menu_________");
		System.out.println("	1.글 작성하기"); //insert
		System.out.println("	2.글 수정하기"); //update
		System.out.println("	3.글 검색하기"); //select
		System.out.println("	4.전체 글 조회하기"); //select
		System.out.println("	5.글 삭제하기"); //delete
		System.out.println("	0.종료하기"); 
		System.out.println("_____________________________");
		System.out.println("번호 선택>>");
	}
	
	public void start() {
		
		int input ;
		do {
			displayMenu();
			input = sc.nextInt();
			switch(input) {
			case 1:
				insertBoard();
				break;
			case 2:
				updateBoard();
				break;
			case 3:
				searchBoard();
				break;
			case 4:
				AllBoardList();
				break;
			case 5:
				deleteBoard();
				break;
			case 0:
				System.out.println("프로그램 종료");
				break;
			default:
				System.out.println("잘못 입력하셨습니다. 다시 입력해 주세요.");
			}
		}while(input != 0);
	}

	
//전체글 조회	
	private void AllBoardList() {
		System.out.println();
		System.out.println("--------------------------------------------");
		System.out.println(" 번호\t작성자\t제목\t내용\t날짜");
		System.out.println("---------------------------------------------");
		
		List<BoardVO> boardList = boardService.AllBoardList();
		
		if(boardList.size() == 0) {
			System.out.println("등록된 게시물이 없습니다.");
		}
		
		for(BoardVO bv:boardList) {
			System.out.println(bv.getBoardno()+ "\t" + bv.getBoardwriter()+ "\t" + bv.getTitle()+"\t"+bv.getDate());
			
		}
		
		System.out.println("-------------------------------------------");
	}

//게시글 검색
	private void searchBoard() {
		sc.nextLine();
		
		System.out.println();
		System.out.println("검색할 게시글번호 입력>>");
		String boardno = sc.nextLine();
		
		System.out.println("작성자 >>");
		String boardwriter = sc.nextLine();
		
		System.out.println("제목>>");
		String title = sc.nextLine();
		
		BoardVO bv = new BoardVO();
		bv.setBoardno(boardno);
		bv.setBoardwriter(boardwriter);
		bv.setTitle(title);
		
		List<BoardVO> boardList = boardService.searchBoard(bv);
		
		if(boardList.size() > 0) {
			for(BoardVO bv2 : boardList) {
				System.out.println(bv2.getBoardno() + "\t"
						         + bv2.getTitle() + "\t"
						         + bv2.getContent() + "\t"
						         + bv2.getBoardwriter() + "\t"
						         + bv2.getDate());
				
				}
			
			}else {
				System.out.println("검색된 결과 없음..");
		}
		
	}
	

//게시글 삭제하기
	private void deleteBoard() {
		
		boolean chk = false;
		
		AllBoardList();
		
		System.out.println("삭제할 게시글 번호 입력>>");
		String boardno = sc.next();
		
		if(!checkBoard(boardno)) {
			System.out.println(boardno + "번 게시글은 없는 게시글입니다.");
		}
		
		int cnt = boardService.deleteBoard(boardno);
		
		if(cnt > 0) {
			System.out.println("삭제되었습니다.");
		}else {
			System.out.println("삭제 실패");
		}
	}

	
//게시글 수정하기	
	private void updateBoard() {
		
		boolean chk = false;
		
		AllBoardList();
		
		System.out.println("수정할 게시글번호 입력>>");
		String boardno = sc.nextLine();
		
		//게시글 번호 존재 여부
		if(!checkBoard(boardno)) {
			System.out.println(boardno + "번 게시글은 없는 글입니다.");
			return;
		}
		
		System.out.println("수정할 제목 입력>>");
		String title = sc.nextLine();
		System.out.println("수정할 내용 입력>>");
		String content = sc.nextLine();
		
		BoardVO bv = new BoardVO();
		bv.setTitle(title);
		bv.setContent(content);
		
		int cnt = boardService.updateBoard(bv);
		if(cnt > 0) {
			System.out.println("게시글이 수정되었습니다.");
		}else {
			System.out.println("게시글수정 실패!!");
		}
	}

	//등록하기 insert
	private void insertBoard() {
		System.out.println("제목 입력>>");
		String title = sc.next();
		
		System.out.println("작성자 입력>>");
		String writer = sc.next();
		
		System.out.println("내용 입력>>");
		String content = sc.next();
		
		BoardVO bv = new BoardVO();
		bv.setBoardwriter(writer);
		bv.setTitle(title);
		bv.setContent(content);
		
		int cnt = boardService.insertBoard(bv);
		
		System.out.println();
		
		if(cnt > 0) {
			System.out.println("게시글 등록 완료");
		}else {
			System.out.println("게시글 등록 실패!!");
		}
	}
	
//게시글 존재여부를 알려주는 메서드	
	public boolean checkBoard(String boardno) {
		
		boolean chk = false;
		
		chk = boardService.checkBoard(boardno);
		
		return chk;
	}
	
	public static void main(String[] args) {
		BoardMain bm = new BoardMain();
		bm.start();
	}
	
}

