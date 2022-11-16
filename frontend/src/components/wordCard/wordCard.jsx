import './wordCard.scss';
export default function WordCard({ word, translation }) {
	return (
		<div className='word-card'>
			<p className='main-word'>{word.word}</p>
			<div className='tiny-topography'>
				{word.partOfSpeech} {word.transcription}
			</div>
			<div className='card-info'>
				<h3 className='card-detail'>Meaning</h3>
				<div className='detail-info'>{word.meaning}</div>
			</div>
			<div className='card-info'>
				<h3 className='card-detail'>Synonyms</h3>
				<div className='detail-info'>{word.synonyms}</div>
			</div>
			<div className='card-info'>
				<h3 className='card-detail'>Example</h3>
				<div className='detail-info'>{word.example}</div>
			</div>
			{translation ? (
				<div className='card-info'>
					<h3 className='card-detail'>Translation</h3>
					<div className='detail-info'>
						{translation.word}
						<div className='tiny-topography-lite'>
							{translation.partOfSpeech} {translation.transcription}
						</div>
					</div>
				</div>
			) : null}
		</div>
	);
}
