package br.com.caelum.tubaina.parser.html.desktop;

import br.com.caelum.tubaina.ParseType;
import br.com.caelum.tubaina.chunk.AnswerChunk;
import br.com.caelum.tubaina.chunk.BoxChunk;
import br.com.caelum.tubaina.chunk.CenteredParagraphChunk;
import br.com.caelum.tubaina.chunk.CodeChunk;
import br.com.caelum.tubaina.chunk.ExerciseChunk;
import br.com.caelum.tubaina.chunk.ImageChunk;
import br.com.caelum.tubaina.chunk.IndexChunk;
import br.com.caelum.tubaina.chunk.IntroductionChunk;
import br.com.caelum.tubaina.chunk.ItemChunk;
import br.com.caelum.tubaina.chunk.ListChunk;
import br.com.caelum.tubaina.chunk.NoteChunk;
import br.com.caelum.tubaina.chunk.ParagraphChunk;
import br.com.caelum.tubaina.chunk.QuestionChunk;
import br.com.caelum.tubaina.chunk.SubsectionChunk;
import br.com.caelum.tubaina.chunk.TableChunk;
import br.com.caelum.tubaina.chunk.TableColumnChunk;
import br.com.caelum.tubaina.chunk.TableRowChunk;
import br.com.caelum.tubaina.chunk.TodoChunk;
import br.com.caelum.tubaina.parser.IntroductionTag;
import br.com.caelum.tubaina.parser.NullAnswerTag;
import br.com.caelum.tubaina.parser.NullNoteTag;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.Tag;
import br.com.caelum.tubaina.parser.TubainaModule;
import br.com.caelum.tubaina.util.HtmlSanitizer;
import br.com.caelum.tubaina.util.Sanitizer;

import com.google.inject.TypeLiteral;

public class HtmlModule extends TubainaModule {

	private boolean showNotes;
	private boolean noAnswer;

	public HtmlModule(boolean showNotes, boolean noAnswer) {
		this.showNotes = showNotes;
		this.noAnswer = noAnswer;
	}
	
	public HtmlModule() {
		this(true, false);
	}
	
	@Override
	protected void configure() {
		bind(new TypeLiteral<Tag<AnswerChunk>>() {}).to(noAnswer ? NullAnswerTag.class : AnswerTag.class);
		bind(new TypeLiteral<Tag<BoxChunk>>() {}).to(BoxTag.class);
		bind(new TypeLiteral<Tag<CenteredParagraphChunk>>() {}).to(CenteredParagraphTag.class);
		bind(new TypeLiteral<Tag<CodeChunk>>() {}).to(CodeTag.class);
		bind(new TypeLiteral<Tag<ExerciseChunk>>() {}).to(ExerciseTag.class);
		bind(new TypeLiteral<Tag<ImageChunk>>() {}).to(ImageTag.class);
		bind(new TypeLiteral<Tag<IntroductionChunk>>() {}).to(IntroductionTag.class);
		bind(new TypeLiteral<Tag<ItemChunk>>() {}).to(ItemTag.class);
		bind(new TypeLiteral<Tag<ListChunk>>() {}).to(ListTag.class);
		bind(new TypeLiteral<Tag<NoteChunk>>() {}).to(showNotes ? NoteTag.class : NullNoteTag.class);
		bind(new TypeLiteral<Tag<ParagraphChunk>>() {}).to(ParagraphTag.class);
		bind(new TypeLiteral<Tag<QuestionChunk>>() {}).to(QuestionTag.class);
		bind(new TypeLiteral<Tag<TableColumnChunk>>() {}).to(TableColumnTag.class);
		bind(new TypeLiteral<Tag<TableRowChunk>>() {}).to(TableRowTag.class);
		bind(new TypeLiteral<Tag<TableChunk>>() {}).to(TableTag.class);
		bind(new TypeLiteral<Tag<TodoChunk>>() {}).to(TodoTag.class);
		bind(new TypeLiteral<Tag<IndexChunk>>() {}).to(IndexTag.class);
		bind(new TypeLiteral<Tag<SubsectionChunk>>() {}).to(SubsectionTag.class);
		
		bind(Parser.class).toInstance(ParseType.HTML.getParser());
		bind(Sanitizer.class).to(HtmlSanitizer.class);
	}


}
