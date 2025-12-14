import {render, screen, within} from "../../test-utils.jsx";
import {mockGameAIWithAgent, mockGameAIWithoutAgent} from "../../mocks/handlers";
import GameAI from "./index";

describe("GameAI component", () => {
  test("renders AI data with players, agent info and evaluations", async () => {
    render(<GameAI gameId={mockGameAIWithAgent.gameId} />);

    const title = await screen.findByRole("heading", {
      level: 1,
      name: `Game AI Data - ${mockGameAIWithAgent.event}`,
    });
    expect(title).toBeInTheDocument();

    const playersHeading = screen.getByRole("heading", {
      level: 2,
      name: /Players/i,
    });
    expect(playersHeading).toBeInTheDocument();

    const playersList = screen.getByRole("list");
    const playerItems = within(playersList).getAllByRole("listitem");
    expect(playerItems.map((item) => item.textContent.trim())).toEqual(
      mockGameAIWithAgent.players
    );

    const agentHeading = screen.getByRole("heading", {
      level: 2,
      name: new RegExp(`AI Agent - ${mockGameAIWithAgent.agent.name}`, "i"),
    });
    expect(agentHeading).toBeInTheDocument();

    const evaluationsTable = await screen.findByRole("table");
    const headers = within(evaluationsTable)
      .getAllByRole("columnheader")
      .map((h) => h.textContent.trim());
    expect(headers).toEqual(["Opening", "Mid Game", "End Game", "Average"]);

    const evalRows = within(evaluationsTable)
      .getAllByRole("row")
      .filter((row) => within(row).queryAllByRole("cell").length > 0);
    expect(evalRows).toHaveLength(mockGameAIWithAgent.evaluations.length);

    const {opening, midGame, endGame} = mockGameAIWithAgent.evaluations[0];
    const firstEvalCells = within(evalRows[0]).getAllByRole("cell");
    expect(firstEvalCells[0]).toHaveTextContent(String(opening));
    expect(firstEvalCells[1]).toHaveTextContent(String(midGame));
    expect(firstEvalCells[2]).toHaveTextContent(String(endGame));

    const average = (opening + midGame + endGame) / 3;
    expect(firstEvalCells[3]).toHaveTextContent(String(average));
  });

  test("shows a notice when no AI agent was used", async () => {
    render(<GameAI gameId={mockGameAIWithoutAgent.gameId} />);

    const title = await screen.findByRole("heading", {
      level: 1,
      name: `Game AI Data - ${mockGameAIWithoutAgent.event}`,
    });
    expect(title).toBeInTheDocument();

    const noAgentHeading = await screen.findByRole("heading", {
      level: 2,
      name: /No AI agent used in this match/i,
    });
    expect(noAgentHeading).toBeInTheDocument();

    expect(screen.queryByText(/AI Agent -/i)).not.toBeInTheDocument();
  });
});
