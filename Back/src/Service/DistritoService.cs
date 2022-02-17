using AutoMapper;
using Microsoft.EntityFrameworkCore;
using Model;
using Model.DTOs;
using Persistence.Database;
using Service.Commons;
using Service.Extensions;
using System.Linq;
using System.Threading.Tasks;

namespace Service
{
    public interface IDistritoService
    {
        Task<DataCollection<DistritoDto>> GetAll(int page, int take);
        Task<DistritoDto> GetById(int id);
        Task<DistritoDto> Create(DistritoCreateDto model);
        Task Update(int id, DistritoUpdateDto model);
        Task Remove(int id);

        Task<DataCollection<DistritoDto>> GetAvailables(int page, int take);

    }

    public class DistritoService : IDistritoService
    {
        private readonly ApplicationDbContext _context;
        private readonly IMapper _mapper;

        public DistritoService(
            ApplicationDbContext context,
            IMapper mapper
        )
        {
            _context = context;
            _mapper = mapper;
        }

        public async Task<DataCollection<DistritoDto>> GetAll(int page, int take)
        {
            return _mapper.Map<DataCollection<DistritoDto>>(
                await _context.Distritos.OrderByDescending(x => x.Name)
                              .AsQueryable()
                              .PagedAsync(page, take)
            );
        }

        public async Task<DistritoDto> GetById(int id)
        {
            return _mapper.Map<DistritoDto>(
                await _context.Distritos.SingleAsync(x => x.Id == id)
            );
        }

        public async Task<DistritoDto> Create(DistritoCreateDto model)
        {
            var entry = new Distrito
            {
                Name = model.Name,
                Description = model.Description
            };

            await _context.AddAsync(entry);
            await _context.SaveChangesAsync();

            return _mapper.Map<DistritoDto>(entry);
        }

        public async Task Update(int id, DistritoUpdateDto model)
        {
            var entry = await _context.Distritos.SingleAsync(x => x.Id == id);

            entry.Name = model.Name;
            entry.Description = model.Description;

            await _context.SaveChangesAsync();
        }

        public async Task Remove(int id)
        {
            _context.Remove(new Distrito
            {
                Id = id
            });

            await _context.SaveChangesAsync();
        }

        public async Task<DataCollection<DistritoDto>> GetAvailables(int page, int take)
        {
            return _mapper.Map<DataCollection<DistritoDto>>(
                await _context.Distritos.Where(x => x.Parqueos.Where(y => y.Enable == true).Count() > 0)
                .OrderByDescending(x => x.Name)
                .Include(x => x.Parqueos)
                .AsQueryable()
                .PagedAsync(page, take)
            );
        }
    }
}
